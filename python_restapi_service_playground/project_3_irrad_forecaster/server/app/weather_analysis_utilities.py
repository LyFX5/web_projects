from abc import ABC, abstractmethod
import pandas as pd
from datetime import datetime
from timezonefinder import TimezoneFinder
import pytz
from enum import Enum
from app.db_manager import upload_weather_to_db, load_weather_from_db


API_REQUEST_FREQUENCY = 3 # h


class Features(Enum):
    TIME = "time"
    SOLAR_POWER = "solar_power"
    SOLAR_IRRADIANCE = "solar_irrad"
    # Irradiance
    GHI = "ghi"
    DHI = "dhi"
    DNI = "dni" # 'direct_normal_irradiance (W/m²)'
    # GHI = DHI + DNIcos(θ)
    SHORT_RADIATION = "shortwave_radiation_instant"
    DIRECT_RADIATION = "direct_radiation_instant"
    DIFFUSE_RADIATION = "diffuse_radiation_instant"
    DIRECT_NORMAL_RADIATION = "direct_normal_irradiance_instant"
    TERRESTRIAL_RADIATION = "terrestrial_radiation_instant"
    # Weather
    TEMPERATURE = "temperature_2m"
    HUMIDITI = "relativehumidity_2m"
    RAIN = "rain"
    WEATHERCODE = "weathercode"
    CLOUDCOVER = "cloudcover"
    CLOUDCOVER_LOW = "cloudcover_low"
    CLOUDCOVER_MID = "cloudcover_mid"
    CLOUDCOVER_HIGH = "cloudcover_high"
    WINDSPEED_10 = "windspeed_10m"
    WINDSPEED_80 = "windspeed_80m"
    VAPOR_PRESSURE_DEFICIT = "vapor_pressure_deficit"


class Day:
    def __init__(self, year: int, month: int, day: int):
        self.year = year
        self.month = month
        self.day = day

    def string_representation(self):
        year = str(self.year)
        if self.month < 10:
            month = "0" + str(self.month)
        else:
            month = str(self.month)
        if self.day < 10:
            day = "0" + str(self.day)
        else:
            day = str(self.day)
        return year + "-" + month + "-" + day


class WeatherDataSource(ABC):

    @abstractmethod
    def provide_historical_data(self, start_day: Day, end_day: Day) -> pd.DataFrame:
        ...

    @abstractmethod
    def provide_forecasted_data(self, n_days: int) -> pd.DataFrame:
        ...


class OpenMeteoAPIWrapper(WeatherDataSource):
    def __init__(self, latitude,  longitude, weather_parameters):
        self.latitude = latitude
        self.longitude = longitude
        self.weather_parameters = weather_parameters
        self.timezone = TimezoneFinder().certain_timezone_at(lat=self.latitude, lng=self.longitude)
        self.api_url = f"https://api.open-meteo.com/v1/forecast?latitude={self.latitude}&longitude={self.longitude}&timezone={self.timezone}&hourly="

    def provide_historical_data(self, start_day: Day, end_day: Day) -> pd.DataFrame:
        start_date = start_day.string_representation()
        end_date = end_day.string_representation()
        api_url = self.api_url
        for i in range(len(self.weather_parameters)):
            api_url += self.weather_parameters[i]
            if i < len(self.weather_parameters) - 1:
                api_url += ","
        api_url += f"&timezone={self.timezone}&min={start_date}&max={end_date}"
        weather_data = pd.read_json(api_url)
        weather_data_df = pd.DataFrame()
        weather_data_df[Features.TIME.value] = weather_data["hourly"][Features.TIME.value]
        for parameter in self.weather_parameters:
            weather_data_df[parameter] = weather_data["hourly"][parameter]
        weather_data_df[Features.TIME.value] = pd.to_datetime(weather_data_df[Features.TIME.value], utc=True)
        # weather_data_df = weather_data_df.assign(time=weather_data_df.time.dt.tz_convert(self.timezone_num))
        weather_data_df = weather_data_df.set_index(Features.TIME.value)
        return weather_data_df

    def provide_forecasted_data(self, n_days) -> pd.DataFrame:
        assert 0 < n_days < 7, "Number of days must be in range [0, 7]."
        api_url = self.api_url
        for i in range(len(self.weather_parameters)):
            api_url += self.weather_parameters[i]
            if i < len(self.weather_parameters) - 1:
                api_url += ","
        api_url += f"&forecast_days={n_days}"
        weather_forecast = pd.read_json(api_url)
        weather_forecast_df = pd.DataFrame()
        weather_forecast_df[Features.TIME.value] = weather_forecast["hourly"][Features.TIME.value]
        for parameter in self.weather_parameters:
            weather_forecast_df[parameter] = weather_forecast["hourly"][parameter]
        weather_forecast_df[Features.TIME.value] = pd.to_datetime(weather_forecast_df[Features.TIME.value], utc=True)
        # weather_forecast_df = weather_forecast_df.assign(time=weather_forecast_df.time.dt.tz_convert(self.timezone))
        weather_forecast_df = weather_forecast_df.set_index(Features.TIME.value)
        return weather_forecast_df

    def provide_forecasted_data_by_coords(self, n_days, latitude, longitude) -> pd.DataFrame:
        assert 0 < n_days < 7, "Number of days must be in range [0, 7]."
        timezone = TimezoneFinder().certain_timezone_at(lat=latitude, lng=longitude)
        api_url = f"https://api.open-meteo.com/v1/forecast?latitude={latitude}&longitude={longitude}&timezone={timezone}&hourly="
        for i in range(len(self.weather_parameters)):
            api_url += self.weather_parameters[i]
            if i < len(self.weather_parameters) - 1:
                api_url += ","
        api_url += f"&forecast_days={n_days}"
        weather_forecast = pd.read_json(api_url)
        weather_forecast_df = pd.DataFrame()
        weather_forecast_df[Features.TIME.value] = weather_forecast["hourly"][Features.TIME.value]
        for parameter in self.weather_parameters:
            weather_forecast_df[parameter] = weather_forecast["hourly"][parameter]
        weather_forecast_df[Features.TIME.value] = pd.to_datetime(weather_forecast_df[Features.TIME.value], utc=True)
        # weather_forecast_df = weather_forecast_df.assign(time=weather_forecast_df.time.dt.tz_convert(self.timezone))
        weather_forecast_df = weather_forecast_df.set_index(Features.TIME.value)
        return weather_forecast_df


class DataBaseWeatherSource(WeatherDataSource):
    def __init__(self, latitude,  longitude, weather_parameters):
        self.latitude = latitude
        self.longitude = longitude
        self.weather_parameters = weather_parameters
        self.api_wrapper = OpenMeteoAPIWrapper(self.latitude, self.longitude, self.weather_parameters)

    def provide_historical_data(self, start_day: Day, end_day: Day) -> pd.DataFrame:
        return self.api_wrapper.provide_historical_data(start_day, end_day)

    def provide_forecasted_data(self, n_days: int) -> pd.DataFrame:
        current_time = datetime.now(tz=pytz.timezone(self.api_wrapper.timezone))
        h = current_time.hour
        print(f"[provide_forecasted_data] {h=}")
        if (h % API_REQUEST_FREQUENCY) == 0:
            weather_forecast_df = self.api_wrapper.provide_forecasted_data(n_days)
            results = upload_weather_to_db(weather_forecast_df.reset_index())
            print(f"[provide_forecasted_data] {results=}")
        else:
            weather_forecast_df = load_weather_from_db()
            if weather_forecast_df is None:
                weather_forecast_df = self.api_wrapper.provide_forecasted_data(n_days)
            else:
                weather_forecast_df = weather_forecast_df.set_index(Features.TIME.value)
        return weather_forecast_df


