import os, psutil
bytes_used = psutil.Process(os.getpid()).memory_info().rss
print(f"memory_MiB = {bytes_used / 1024 ** 2} \n"
      f"memory_MB = {bytes_used / 1000 ** 2}")

from waitress import serve
from app import app
from app import db_manager

if __name__ == "__main__":
    # Data Base connection
    table_manager = db_manager.DataBaseManager()
    table_manager.create_connection()
    table_manager.create_cursor()
    table_manager.create_table()
    table_manager.save_changes()
    table_manager.close_connection()

    # Flask app run
    # app.app.run(debug=True, port=1234, host='0.0.0.0')
    serve(app.app, host="0.0.0.0", port=4007)
