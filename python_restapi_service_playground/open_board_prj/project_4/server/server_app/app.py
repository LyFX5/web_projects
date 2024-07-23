from flask import Flask, request, render_template, url_for, get_template_attribute, flash, redirect, render_template, request, session, abort
from .server_utilities import ServerSession


server_session = ServerSession()
app = Flask(__name__, template_folder="./templates")


@app.route('/')
def home():
    if not server.is_address_logged_in(request.remote_addr):
        return render_template('login.html')
    else:
        username = server.get_username_by_addr(request.remote_addr)
        boards_names = server.get_boards_names_by_username(username)
        return render_template('self_user.html',
                               username=username,
                               boards_names=boards_names)

@app.route('/login', methods=['POST'])
def login():
    POST_PUBLIC = str(request.form['public_key'])
    POST_PRIVATE = str(request.form['private_key'])
    server.login_address(request.remote_addr, POST_PUBLIC, POST_PRIVATE)
    return home()

@app.route("/logout")
def logout():
    server.logout(request.remote_addr)
    return home()

@app.route('/find_user', methods=['POST'])
def find_user():
    username = str(request.form['user_name'])
    if server.user_exists(username):
        boards_names = server.get_boards_names_by_username(username)
        return render_template('other_user.html',
                               username=username,
                               boards_names=boards_names)
    return home()

@app.route('/create_board')
def create_board():
    return render_template('board_creation.html',
                               username=server.get_username_by_addr(request.remote_addr)) # TODO

@app.route('/go_to_self_board')
def go_to_self_board():
    return render_template('self_board.html',
                               board_content=server.get_board_content()) # TODO

@app.route('/go_to_other_board')
def go_to_other_board():
    return render_template('other_board.html',
                               board_content=server.get_board_content()) # TODO

@app.route('/find_board')
def find_board():
    return render_template('user_board.html',
                               board_content=server.get_board_content()) # TODO


# @app.route('/message', methods=['POST'])
# def read_message():
#     message = str(request.form['message'])
#     server.save_message(request.remote_addr, message)
#     return home()


# @app.route('/upload_file', methods=['POST'])
# def upload_file():
#     f = request.files['file']
#     f.save(f.filename)
#     print(f.filename)
#     print(f)
#     return home()


