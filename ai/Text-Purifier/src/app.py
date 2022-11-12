import io
import json
import numpy as np
from flask import Flask,render_template,session,redirect,url_for,jsonify,send_from_directory,jsonify,request
from tensorflow import keras
from keras.models import Sequential
from keras.layers import Dense
from keras.layers import Flatten
from keras.layers.convolutional import Conv2D
from keras.layers.convolutional import MaxPooling2D
from keras.preprocessing.image import ImageDataGenerator
from keras.utils import Sequence
from curse_detector import CurseDetector
 
app = Flask(__name__)

@app.route("/getting", methods=['GET'])

def getting():
    return render_template('index.html')

@app.route("/predict", methods=['POST'])

def predict():
    params = json.loads(request.get_data())
    weights_paths = ['models/weights.h5']
    curse = CurseDetector(weights_paths)
    masked_content = curse.masking(params['content'])
    letter = {
        "content": masked_content
    }
    return letter

    # masked_content = curse.masking('씌바 벌레같은 놈아 안죽냐?')
    # return render_template('index.html', data=masked_content)

if __name__ == "__main__": # terminal에서 python 인터프리터로 .py 파일을 실행하면 무조건 이 부분을 찾아 실행합니다.
                           # C의 main
    print(("* Loading Keras model and Flask starting server..."
        "please wait until server has fully started"))
    app.run(host="0.0.0.0") # app.run을 해줘야 flask 서버가 구동됩니다. 
                            # host="0.0.0.0"은 외부에서 해당 서버 ip 주소 접근이 가능하도록 하는 옵션입니다.