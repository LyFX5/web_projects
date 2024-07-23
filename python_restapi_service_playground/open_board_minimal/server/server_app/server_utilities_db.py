from enum import Enum
from dataclasses import dataclass
from typing import List
from .db_manager import DataBaseManager, UsersTableColumns


class LoginCases(Enum):
    NOT_VALID_KEYS = "недос ТаТочНОс имВА лов"
    CREATED = "Добро пожаловать новый пользователь!"
    LOGIN = "И снова здравствуй!"
    INCORRECT_PASSWORD = "Ты пытаешься кого-то взломать!"


# @dataclass
# class User:
#     address: str
#     name: str
#     password: str
#     content: List[str]


class ServerSession:
    def __init__(self):
        self.__data_base = None  # TODO
        # self.__logged_in_addresses: list = []
        # self.__users = []

    def is_address_logged_in(self, addresses) -> bool:
        table_manager = DataBaseManager()
        table_manager.create_connection()
        table_manager.create_cursor()
        result = table_manager.find_in_table(UsersTableColumns.ADDRESS, addresses)
        table_manager.save_changes()
        table_manager.close_connection()
        return len(result) != 0

    def user_exists(self, username: str) -> bool:
        table_manager = DataBaseManager()
        table_manager.create_connection()
        table_manager.create_cursor()
        result = table_manager.find_in_table(UsersTableColumns.NAME, username)
        table_manager.save_changes()
        table_manager.close_connection()
        return len(result) != 0

    def __is_public_key_valid(self, public: str) -> bool:
        return (public is not None) and (len(public) != 0)

    def __is_private_key_valid(self, private: str) -> bool:
        return (private is not None) and (len(private) != 0)

    def __is_private_key_correct(self, public, private) -> bool:
        table_manager = DataBaseManager()
        table_manager.create_connection()
        table_manager.create_cursor()
        result = table_manager.find_in_table(UsersTableColumns.NAME, public)
        table_manager.save_changes()
        table_manager.close_connection()
        return (private == result[0][2])

    def __create_user(self, address: str, public: str, private: str) -> None:
        # self.__users_number += 1
        table_manager = DataBaseManager()
        table_manager.create_connection()
        table_manager.create_cursor()
        table_manager.upload_users_to_table([(address, public, private, "")])
        table_manager.save_changes()
        table_manager.close_connection()

    def login_address(self, address: str, public: str, private: str) -> LoginCases:
        if self.__is_public_key_valid(public) and self.__is_private_key_valid(private):
            if self.user_exists(public):
                if self.__is_private_key_correct(public, private):
                    # TODO update address in data base
                    return LoginCases.LOGIN
                else:
                    return LoginCases.INCORRECT_PASSWORD
            else:
                self.__create_user(address, public, private)
                # TODO update address in data base
                return LoginCases.CREATED
        else:
            return LoginCases.NOT_VALID_KEYS

    def logout_address(self, address: str) -> None:
        # TODO update address to None in data base
        pass

    def get_username_by_addr(self, address: str) -> str:
        table_manager = DataBaseManager()
        table_manager.create_connection()
        table_manager.create_cursor()
        result = table_manager.find_in_table(UsersTableColumns.ADDRESS, address)
        table_manager.save_changes()
        table_manager.close_connection()
        return result[0][1]

    def get_board_content_by_username(self, username: str) -> dict:
        table_manager = DataBaseManager()
        table_manager.create_connection()
        table_manager.create_cursor()
        result = table_manager.find_in_table(UsersTableColumns.NAME, username)
        table_manager.save_changes()
        table_manager.close_connection()
        return result[0][3]

    def send_message_from_user1_to_user2(self, user1, user2, message) -> None:
        # TODO update content in data base
        # TODO message is not empty and user2 exists
        for user in self.__users:
            if user.name == user1 or user.name == user2:
                user.content.append(f"{user1} -> {user2} : {message}")



