from enum import Enum
import sqlite3 as sql
import pandas as pd


class UsersTableColumns(Enum):
    ADDRESS = "address"
    NAME = "name"
    PASSWORD = "password"
    CONTENT = "content"


class DataBaseManager:
    def __init__(self):
        self.db_file_name = "tree_of_storage.db"
        self.cursor = None
        self.connection = None
        self.users_table_name = 'users'
        self.tables_creation_queries = {
                                        self.users_table_name: f'''CREATE TABLE {self.users_table_name}
                                    ({UsersTableColumns.ADDRESS.value} text,
                                    {UsersTableColumns.NAME.value} text,
                                    {UsersTableColumns.PASSWORD.value} text,
                                    {UsersTableColumns.CONTENT.value} text)'''
        }


    def create_connection(self):
        self.connection = sql.connect(self.db_file_name)

    def create_cursor(self):
        assert self.connection is not None, 'connection is not created'
        self.cursor = self.connection.cursor()

    def table_not_exists(self, table_name) -> bool:
        assert self.cursor is not None, 'cursor is not created'
        self.cursor.execute('''SELECT name  FROM sqlite_master WHERE type='table';''')
        db = self.cursor.fetchall()
        existing_tables_names = [table_tuple[0] for table_tuple in db]
        return table_name not in existing_tables_names

    def create_tables(self):
        for table_name in self.tables_creation_queries.keys():
            if self.table_not_exists(table_name):
                self.cursor.execute(self.tables_creation_queries[table_name])
                print(f'Table {table_name} is created.')

    def save_changes(self):
        # Save (commit) the changes
        self.connection.commit()

    def close_connection(self):
        # We can also close the connection if we are done with it.
        # Just be sure any changes have been committed or they will be lost.
        self.connection.close()

    def upload_users_to_table(self, list_of_rows: list):
        [address, public, private, content] = list_of_rows[0]
        # the list_of_rows must be a list of tuples (is a subtitles of the video)
        # ID = list_of_rows[0][0]
        # query = f"SELECT EXISTS(SELECT 1 FROM {self.users_table_name} WHERE {UsersTableColumns.NAME.value}={public} LIMIT 1)"
        # res = self.cursor.execute(query).fetchall()
        # #print(res)
        # name_not_exists = res[0][0] == 0
        if True:
            query = f"INSERT INTO {self.users_table_name} VALUES (?, ?, ?, ?)"
            self.cursor.executemany(query, list_of_rows)

    # def upload_videos_to_table(self, videos):
    #
    #     for list_of_rows in tqdm(videos):
    #         self.upload_video_to_table(list_of_rows)
    #
    #     print(f'All {len(videos)} videos were uploaded to the database.')
    #
    def find_in_table(self, column: UsersTableColumns, value):
        query = f'''
                 SELECT * FROM {self.users_table_name}
                 WHERE {column.value} like '{value}'
                 '''
        return self.cursor.execute(query).fetchall()
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


