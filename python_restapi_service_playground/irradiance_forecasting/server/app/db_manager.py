from enum import Enum
import sqlite3 as sql
import pandas as pd
from tqdm import tqdm


class WeatherTableColumns(Enum):
    TIME = "time"
    SOLAR_POWER = "solar_power"
    SOLAR_IRRADIANCE = "solar_irrad"
    # Irradiance
    GHI = "ghi"
    DHI = "dhi"
    DNI = "dni"  # 'direct_normal_irradiance (W/m²)'
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


class DataBaseManager:
    def __init__(self):
        self.db_file_name = "weather.db"
        self.table_name = 'current_day_hourly_weather'
        self.cursor = None
        self.connection = None

    def create_connection(self):
        self.connection = sql.connect(self.db_file_name)

        # def dict_factory(cursor, row):
        #     # обертка для преобразования
        #     # полученной строки. (взята из документации)
        #     d = {}
        #     for idx, col in enumerate(cursor.description):
        #         d[col[0]] = row[idx]
        #     return d
        #
        # self.connection.row_factory = dict_factory

    def create_cursor(self):
        assert self.connection is not None, 'connection is not created'
        self.cursor = self.connection.cursor()

    def table_not_exists(self) -> bool:
        assert self.cursor is not None, 'cursor is not created'
        self.cursor.execute('''SELECT name  FROM sqlite_master WHERE type='table';''')
        db = self.cursor.fetchall()
        print(f'existing tables {db}')
        return len(db) == 0 or self.table_name not in db[0] # [table['name'] for table in db]

    def create_table(self):
        if self.table_not_exists():
            query = f'''CREATE TABLE {self.table_name}
                        ({WeatherTableColumns.TIME.value} date,
                        {WeatherTableColumns.SHORT_RADIATION.value} float,
                        {WeatherTableColumns.DIRECT_RADIATION.value} float,
                        {WeatherTableColumns.DIFFUSE_RADIATION.value} float,
                        {WeatherTableColumns.DIRECT_NORMAL_RADIATION.value} float,
                        {WeatherTableColumns.TERRESTRIAL_RADIATION.value} float,
                        {WeatherTableColumns.TEMPERATURE.value} float,
                        {WeatherTableColumns.HUMIDITI.value} float,
                        {WeatherTableColumns.RAIN.value} float,
                        {WeatherTableColumns.WEATHERCODE.value} float,
                        {WeatherTableColumns.CLOUDCOVER.value} float,
                        {WeatherTableColumns.CLOUDCOVER_LOW.value} float,
                        {WeatherTableColumns.CLOUDCOVER_MID.value} float,
                        {WeatherTableColumns.CLOUDCOVER_HIGH.value} float,
                        {WeatherTableColumns.WINDSPEED_10.value} float,
                        {WeatherTableColumns.WINDSPEED_80.value} float,
                        {WeatherTableColumns.VAPOR_PRESSURE_DEFICIT.value} float)'''
            self.cursor.execute(query)
        print(f'Table {self.table_name} is created.')

    def save_changes(self):
        # Save (commit) the changes
        self.connection.commit()

    def close_connection(self):
        # We can also close the connection if we are done with it.
        # Just be sure any changes have been committed or they will be lost.
        self.connection.close()
    #
    # def upload_video_to_table(self, list_of_rows: list):
    #     # the list_of_rows must be a list of tuples (is a subtitles of the video)
    #
    #     ID = list_of_rows[0][0]
    #     query = f"SELECT EXISTS(SELECT 1 FROM {self.table_name} WHERE {Column.ID.value}={ID} LIMIT 1)"
    #     res = self.cursor.execute(query).fetchall()
    #     #print(res)
    #     id_not_exists = res[0][0] == 0
    #
    #     if id_not_exists:
    #         query = f"INSERT INTO {self.table_name} VALUES (?, ?, ?, ?, ?, ?)"
    #         self.cursor.executemany(query, list_of_rows)

    # def upload_videos_to_table(self, videos):
    #
    #     for list_of_rows in tqdm(videos):
    #         self.upload_video_to_table(list_of_rows)
    #
    #     print(f'All {len(videos)} videos were uploaded to the database.')
    #
    # def find_in_table(self, column: Column, value):
    #
    #     query = f'''
    #              SELECT * FROM {self.table_name}
    #              WHERE {column.value} like '{value}'
    #              '''
    #
    #     return self.cursor.execute(query).fetchall()
    #
    # def find_string_usage_in_subtitles(self, string: str):
    #
    #     try:
    #         results = self.find_in_table(column=Column.CONTENT, value=f'% {string} %')
    #     except Exception as e: #TODO
    #         return None
    #
    #     if len(results) == 0:
    #         return None
    #     elif len(results[0]) == 0:
    #         return None
    #     else:
    #         return results

    def upload_weather_to_table(self, hourly_day_weather: pd.DataFrame):
        # query = f"SELECT EXISTS(SELECT 1 FROM {self.table_name} WHERE {Column.ID.value}={ID} LIMIT 1)"
        # res = self.cursor.execute(query).fetchall()
        # # print(res)
        # id_not_exists = res[0][0] == 0
        #
        # if id_not_exists:
        #     query = f"INSERT INTO {self.table_name} VALUES (?, ?, ?, ?, ?, ?)"
        #     self.cursor.executemany(query, list_of_rows)
        # pass
        return hourly_day_weather.to_sql(self.table_name, self.connection, if_exists='replace', index=False)

    def load_weather_from_table(self) -> pd.DataFrame:
        return pd.read_sql(f'select * from {self.table_name}', self.connection)

def upload_weather_to_db(hourly_day_weather: pd.DataFrame):
    table_manager = DataBaseManager()
    table_manager.create_connection()
    table_manager.create_cursor()
    results = table_manager.upload_weather_to_table(hourly_day_weather)
    table_manager.save_changes()
    table_manager.close_connection()
    return results

def load_weather_from_db() -> pd.DataFrame:
    table_manager = DataBaseManager()
    table_manager.create_connection()
    table_manager.create_cursor()
    hourly_day_weather = table_manager.load_weather_from_table()
    table_manager.close_connection()
    return hourly_day_weather



