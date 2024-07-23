from enum import Enum
from dataclasses import dataclass
from typing import List


class LoginCases(Enum):
    NOT_VALID_KEYS = "недос ТаТочНОс имВА лов"
    CREATED = "Добро пожаловать новый пользователь!"
    LOGIN = "И снова здравствуй!"
    INCORRECT_PASSWORD = "Ты пытаешься кого-то взломать!"


@dataclass
class User:
    address: str
    name: str
    password: str
    content: List[str]


users_data_base: List[User] = [] # TODO serialize deserialize (correctly save media (pdf, documents, video, music, fotos))


class ServerSession:
    def __init__(self):
        self.__logged_in_users: List[User] = []

    def is_address_logged_in(self, address) -> bool:
        return (address in [user.address for user in self.__logged_in_users])

    def user_exists(self, username: str) -> bool:
        return (username in [user.name for user in users_data_base])

    def __is_public_key_valid(self, public: str) -> bool:
        return (public is not None) and (len(public) != 0)

    def __is_private_key_valid(self, private: str) -> bool:
        return (private is not None) and (len(private) != 0)

    def __is_private_key_correct(self, public, private) -> bool:
        for user in users_data_base:
            if user.name == public and user.password == private:
                return True
        return False

    def __create_user(self, address: str, public: str, private: str) -> None:
        users_data_base.append(User(address=address, name=public, password=private, content=[]))

    def __get_user_from_data_base(self, public: str) -> User:
        for user in users_data_base:
            if user.name == public:
                return user

    def login_address(self, address: str, public: str, private: str) -> LoginCases:
        if self.__is_public_key_valid(public) and self.__is_private_key_valid(private):
            if self.user_exists(public):
                if self.__is_private_key_correct(public, private):
                    logged_in_user = self.__get_user_from_data_base(public)
                    logged_in_user.address = address
                    self.__logged_in_users.append(logged_in_user)
                    return LoginCases.LOGIN
                else:
                    return LoginCases.INCORRECT_PASSWORD
            else:
                self.__create_user(address, public, private)
                self.__logged_in_users.append(self.__get_user_from_data_base(public))
                return LoginCases.CREATED
        else:
            return LoginCases.NOT_VALID_KEYS

    def logout_address(self, address: str) -> None:
        user = None
        for user in users_data_base:
            if user.address == address:
                break
        assert user is not None
        self.__logged_in_users.remove(user)
        user.address = None

    def get_username_by_addr(self, address: str) -> str:
        for user in users_data_base:
            if user.address == address:
                return user.name

    def get_board_content_by_username(self, username: str) -> list:
        for user in users_data_base:
            if user.name == username:
                return user.content

    def send_message_from_user1_to_user2(self, user1, user2, message) -> None:
        if len(user1) > 0 and len(user2) > 0 and len(message) > 0:
            for user in users_data_base:
                if user.name == user1 or user.name == user2:
                    user.content.append(f"{user1} -> {user2} : {message}")


