
from waitress import serve
from server_app import app
from server_app import db_manager


if __name__ == "__main__":
    # Data Base connection
    table_manager = db_manager.DataBaseManager()
    table_manager.create_connection()
    table_manager.create_cursor()
    table_manager.create_tables()
    table_manager.save_changes()
    table_manager.close_connection()

    # Flask app run
    app.app.run(host="0.0.0.0",  port=1234)
    # serve(app.app, host="0.0.0.0", port=1234)


