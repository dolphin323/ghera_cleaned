from flask import render_template
from flask import Flask
from flask import request
app = Flask(__name__)

def shutdown_server():
    func = request.environ.get('werkzeug.server.shutdown')
    if func is None:
        raise RuntimeError('Not running serever')
    func()

@app.route('/test/')
@app.route('/test/<name>')
def test(name=None):
    return render_template('landingPage.html', name=name)

@app.route('/index/')
@app.route('/index/<name>')
def index(name=None):
    return render_template('index.html', name=name)

@app.route('/file-access-js/')
@app.route('/file-access-js/<name>')
def js(name=None):
    return render_template('fileAccess.js',name=name)

@app.route('/location/')
@app.route('/location/<name>')
def loc(name=None):
    return render_template('displayLocation.html', name=name)

@app.route('/shutdown')
def shutdown():
    shutdown_server()
    return 'Server shutting down...'


app.run('localhost', debug=True, port=5000, ssl_context=('./keys/keyExpired-publickey.crt', './keys/keyExpired-privatekey.key'))
