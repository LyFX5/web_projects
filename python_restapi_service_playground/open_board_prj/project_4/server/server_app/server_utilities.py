
class ServerSession:
    def __init__(self):
        self.__data_base = None  # TODO
        self.__session_address: str = None
        self.__is_address_logged_in: bool = False

    @property
    def is_address_logged_in(self) -> bool:
        return self.__is_address_logged_in

    def user_exists(self, username: str) -> bool:
        # TODO
        pass

    def __is_public_key_valid(self, public: str) -> bool:
        return (public is not None) and (len(public) != 0)

    def __is_private_key_valid(self, private: str) -> bool:
        return (private is not None) and (len(private) != 0)

    def __is_private_key_correct(self, public, private) -> bool:
        # TODO
        pass

    def __create_user(self, address: str, public: str, private: str) -> None:
        # TODO
        pass

    def login_address(self, address: str, public: str, private: str) -> None:
        self.session_address = address
        if self.__is_public_key_valid(public) and self.__is_private_key_valid(private):
            if self.user_exists(public):
                if self.__is_private_key_correct(public, private):
                    self.__is_address_logged_in = True # TODO
            else:
                self.__create_user(address, public, private)

    def logout_address(self, address):
        # TODO
        pass







def save_message(address, message):
    messages.append(message)
    print((address, message))



def get_username_by_addr(address):
    return logged_addresses[address]["public"]

def get_messages_by_addr(address):
    return messages

def get_messages_by_username(username):
    if username == "tree_for_peace":
        return ["solnce", "vzoshlos", "skoro", "https://mlcourse.ai/book/topic09/topic9_part1_time_series_python.html", "https://www.sciencedirect.com/science/article/pii/S2352484721001219"]
    else:
        return messages

def get_boards_names_by_username(username):
    return ["solnce", "vzoshlos", "skoro"]

