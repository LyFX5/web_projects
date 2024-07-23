import pandas as pd
import numpy as np
from lightgbm import LGBMRegressor
from sklearn.model_selection import train_test_split
from fedot.api.main import Fedot
from fedot.core.data.data import InputData
from fedot.core.data.data_split import train_test_data_setup
from fedot.core.pipelines.pipeline_builder import PipelineBuilder
from fedot.core.repository.tasks import Task, TaskTypesEnum, TsForecastingParams

data_sample = {
    'time': [pd.Timestamp(year=2022, month=1, day=1, hour=9, tz='UTC'), pd.Timestamp(year=2022, month=1, day=1, hour=10, tz='UTC')],
    'shortwave_radiation': [260.0, 436.0],
    'direct_radiation': [179.0, 328.0],
    'diffuse_radiation': [81.0, 108.0],
    'direct_normal_irradiance': [563.9, 662.1],
    'temperature_2m': [22.8, 25.0],
    'relativehumidity_2m': [77.0, 65.0],
    'rain': [0.0, 0.0],
    'weathercode': [1.0, 0.0],
    'cloudcover': [22.0, 20.0],
    'cloudcover_low': [0.0, 0.0],
    'cloudcover_mid': [1.0, 1.0],
    'cloudcover_high': [72.0, 63.0],
    'windspeed_10m': [0.8, 0.8],
    'vapor_pressure_deficit': [0.63, 1.11],
    'solar_irrad': [504.646, 753.456]
     }
data_sample = pd.DataFrame(data=data_sample)
data_sample = data_sample.set_index("time")

class WeatherBasedIrradianceModel:
    def __init__(self,
                 independent_features: list[str],
                 target_feature: str,
                 init_model: LGBMRegressor,
                 data_for_model_building: pd.DataFrame = data_sample):
        self.independent_features = independent_features
        self.target_feature = target_feature
        self.__model = LGBMRegressor()
        data = data_for_model_building[[self.target_feature] + self.independent_features]
        data = data.dropna()
        x_train, x_test, y_train, y_test = train_test_split(data[self.independent_features], data[self.target_feature], test_size=0.5, random_state=42)
        self.__model.fit(x_train, y_train, init_model=init_model)
        prediction = self.__model.predict(x_test)

    def irradiance_by_weather(self, weather_data: pd.DataFrame) -> pd.DataFrame :
        return self.__model.predict(weather_data)


class HistoryBasedIrradianceModel:
    def __init__(self, init_model):
        self.__model = init_model

    def irradiance_by_history(self, history: np.ndarray) -> float:
        return self.__model.forecast(pre_history=history).item()

