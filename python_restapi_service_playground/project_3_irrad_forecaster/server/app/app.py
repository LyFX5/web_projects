from flask import Flask, request, jsonify
from app.inference_psh import Inference

inference = Inference(daily_init_model_path="app/irradiance_daily_mean_init_model_psh.pkl",
                                         hourly_init_model_path="app/irradiance_hourly_mean_init_model_psh.pkl")

app = Flask(__name__)

@app.route('/prediction_day', methods=['GET'])
def get_prediction_day():
    current_day_mean_irradiance = inference.predict_current_day_mean_irradiance()
    prediction = [
    {"current_day_mean_irrad": current_day_mean_irradiance}
     ]
    return jsonify(prediction)

@app.route('/prediction_rest_day', methods=['GET'])
def get_prediction_rest_day():
    [rest_of_day_mean_irradiance,
     current_hour, rest_of_day_mean_pv_power, rest_of_day_energy] = inference.predict_rest_of_day_mean_irradiance()
    prediction = [
    {"day_rest_mean_irrad": rest_of_day_mean_irradiance,
     "current_hour": current_hour,
     "day_rest_mean_pv_power": rest_of_day_mean_pv_power,
     "day_rest_energy": rest_of_day_energy}
     ]
    return jsonify(prediction)

@app.route('/prediction_hour', methods=['GET'])
def get_prediction_hour():
    args = request.args
    irrad_1 = float(args.get('irrad_1'))
    irrad_2 = float(args.get('irrad_2'))
    irrad_3 = float(args.get('irrad_3'))
    irrad_4 = float(args.get('irrad_4'))
    irrad_5 = float(args.get('irrad_5'))
    irrad_6 = float(args.get('irrad_6'))
    irrad_7 = float(args.get('irrad_7'))
    irrad_8 = float(args.get('irrad_8'))
    irrad_9 = float(args.get('irrad_9'))
    irrad_10 = float(args.get('irrad_10'))
    irrad_11 = float(args.get('irrad_11'))
    irrad_12 = float(args.get('irrad_12'))
    next_hour_mean_irradiance = inference.predict_next_hour_mean_irradiance([irrad_12, irrad_11, irrad_10, irrad_9, irrad_8, irrad_7, irrad_6, irrad_5, irrad_4, irrad_3, irrad_2, irrad_1])
    prediction = [
    {"next_hour_mean_irrad": next_hour_mean_irradiance}
     ]
    return jsonify(prediction)

@app.route('/weather_psh', methods=['GET'])
def get_weather_psh():
    return jsonify([inference.weather_data_source.provide_forecasted_data(1).reset_index().to_dict()])

@app.route('/weather', methods=['GET'])
def get_weather():
    args = request.args
    n_days = int(args.get('n_days'))
    latitude = round(float(args.get('latitude')), 3)
    longitude = round(float(args.get('longitude')), 3)
    return jsonify([inference.weather_data_source.provide_forecasted_data_by_coords(n_days, latitude, longitude).reset_index().to_dict()])

