from flask import Flask, request, render_template, url_for, get_template_attribute, flash, redirect, render_template, request, session, abort
from .server_utilities_list import ServerSession, LoginCases


server_session = ServerSession()
app = Flask(__name__, template_folder="./templates")


@app.route('/')
def home():
    if not server_session.is_address_logged_in(request.remote_addr):
        return render_template('login.html')
    else:
        username = server_session.get_username_by_addr(request.remote_addr)
        board_content = server_session.get_board_content_by_username(username)
        return render_template('board.html',
                               username=username,
                               board_content=board_content)

@app.route('/login', methods=['POST'])
def login():
    POST_PUBLIC = str(request.form['public_key'])
    POST_PRIVATE = str(request.form['private_key'])
    login_case = server_session.login_address(request.remote_addr, POST_PUBLIC, POST_PRIVATE)
    # flash(login_case.value)
    return home()

@app.route("/logout")
def logout():
    server_session.logout_address(request.remote_addr)
    return home()

# @app.route('/find_user', methods=['POST'])
# def find_user():
#     username = str(request.form['user_name'])
#     if server_session.user_exists(username):
#         board_content = server_session.get_board_content_by_username(username)
#         return render_template('board.html',
#                                username=username,
#                                board_content=board_content)
#     flash("нет такого мана")
#     return home()

@app.route('/send_message', methods=['POST'])
def send_message():
    username = server_session.get_username_by_addr(request.remote_addr)
    destination = str(request.form['destination'])
    message = str(request.form['message'])
    server_session.send_message_from_user1_to_user2(user1=username, user2=destination, message=message)
    return home()

@app.route('/post_file', methods=['POST'])
def post_file():
    # TODO
    f = request.files['file']
    f.save(f.filename)
    print(f.filename)
    print(f)
    return home()


