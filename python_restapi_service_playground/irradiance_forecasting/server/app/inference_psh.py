from app.weather_analysis_utilities import OpenMeteoAPIWrapper, Features, DataBaseWeatherSource
from app.models import WeatherBasedIrradianceModel, HistoryBasedIrradianceModel
import pickle
import numpy as np
import pandas as pd
from datetime import datetime
from timezonefinder import TimezoneFinder
import pytz


LATITUDE_PSH = 18.852
LONGITUDE_PSH = 98.994
IRRAD_TO_PVPOWER_SCALER_PSH = 39


class Inference:
    def __init__(self, daily_init_model_path, hourly_init_model_path):
        self.irrad_to_pvpower_scaler = IRRAD_TO_PVPOWER_SCALER_PSH
        self.FEATURES = [Features.TIME.value,
                         Features.SOLAR_POWER.value,
                         Features.SOLAR_IRRADIANCE.value,
                         Features.GHI.value,
                         Features.DHI.value,
                         Features.DNI.value,
                         Features.SHORT_RADIATION.value,
                         Features.DIRECT_RADIATION.value,
                         Features.DIFFUSE_RADIATION.value,
                         Features.DIRECT_NORMAL_RADIATION.value,
                         # Features.TERRESTRIAL_RADIATION.value,
                         Features.TEMPERATURE.value,
                         Features.HUMIDITI.value,
                         Features.RAIN.value,
                         Features.WEATHERCODE.value,
                         Features.CLOUDCOVER.value,
                         Features.CLOUDCOVER_LOW.value,
                         Features.CLOUDCOVER_MID.value,
                         Features.CLOUDCOVER_HIGH.value,
                         Features.WINDSPEED_10.value,
                         # Features.WINDSPEED_80.value,
                         Features.VAPOR_PRESSURE_DEFICIT.value]
        self.latitude = LATITUDE_PSH
        self.longitude = LONGITUDE_PSH
        self.timezone = TimezoneFinder().certain_timezone_at(lat=self.latitude, lng=self.longitude)
        self.weather_parameters = ["shortwave_radiation",
                                   "direct_radiation",
                                   "diffuse_radiation",
                                   "direct_normal_irradiance"] + self.FEATURES[10:]
        self.weather_data_source = DataBaseWeatherSource(self.latitude, self.longitude, self.weather_parameters)
        with open(daily_init_model_path, "rb") as init_model_file:
            init_model = pickle.load(init_model_file)
            self.weather_based_model = WeatherBasedIrradianceModel(self.weather_parameters, Features.SOLAR_IRRADIANCE.value, init_model)
        with open(hourly_init_model_path, "rb") as init_model_file:
            init_model = pickle.load(init_model_file)
            self.history_based_model = HistoryBasedIrradianceModel(init_model)

    def predict_current_day_mean_irradiance(self) -> float:
        day_weather_data = self.weather_data_source.provide_forecasted_data(1)
        day_weather_data = day_weather_data.resample('24H').mean()
        day_mean_irradiance = self.weather_based_model.irradiance_by_weather(day_weather_data).item()
        return day_mean_irradiance

    def predict_rest_of_day_mean_irradiance(self) -> list[float]:
        day_weather_data = self.weather_data_source.provide_forecasted_data(1)
        one_day = day_weather_data.reset_index()
        one_day = one_day.drop(columns=[Features.TIME.value])
        current_time = datetime.now(tz=pytz.timezone(self.timezone))
        print(f"{self.timezone=}")
        print(f"{current_time=}")
        y = current_time.year
        m = current_time.month
        d = current_time.day
        h = current_time.hour
        tail_df = pd.DataFrame(columns=(list(day_weather_data.columns)))
        tail_mean = one_day.tail(24 - h)
        # print(tail_mean)
        tail_mean = tail_mean.mean()
        tail_df = pd.concat([tail_df,
                                pd.DataFrame.from_dict({0: list(tail_mean.values)}, orient='index',
                                                       columns=list(tail_mean.index))])
        tail_df[Features.TIME.value] = pd.Timestamp(year=y, month=m, day=d, hour=h, tz='UTC')
        tail_df = tail_df.set_index(Features.TIME.value)
        # print(tail_df)
        # print()
        rest_of_day_mean_irradiance = self.weather_based_model.irradiance_by_weather(tail_df).item()
        rest_of_day_mean_irradiance = max(0, rest_of_day_mean_irradiance)
        rest_of_day_mean_pv_power = self.irrad_to_pvpower_scaler * rest_of_day_mean_irradiance
        rest_of_day_energy = (24-h) * rest_of_day_mean_pv_power
        return [rest_of_day_mean_irradiance, h, rest_of_day_mean_pv_power, rest_of_day_energy]

    def predict_next_hour_mean_irradiance(self, history: list[float]) -> float:
        hour_mean_irradiance = self.history_based_model.irradiance_by_history(np.array(history))
        return hour_mean_irradiance


