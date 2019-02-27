from flask import render_template
from flask import Flask
from flask import request
from flask_httpauth import HTTPBasicAuth

app = Flask(__name__)
auth = HTTPBasicAuth()

def shutdown_server():
    func = request.environ.get('werkzeug.server.shutdown')
    if func is None:
        raise RuntimeError('Not running serever')
    func()

users = {
    "john": "hello",
    "susan": "bye"
}

@auth.get_password
def get_pw(username):
    print(username)
    if username in users:
        return users.get(username)
    return None

@app.route('/<ssn>')
@auth.login_required
def secureEntry(ssn):
    return "Hello, %s! SSN=%s" % (auth.username(), ssn)

@app.route('/test/')
@app.route('/test/<name>')
def test(name=None):
    return render_template('landingPage.html', name=name)

@app.route('/modify-key/')
@app.route('/modify-key/<name>')
def some(name=None):
    return render_template('modifyKey.html', name=name)

@app.route('/file-access-js/')
@app.route('/file-access-js/<name>')
def js(name=None):
    return render_template('fileAccess.js',name=name)

@app.route('/key-access-js/')
@app.route('/key-access-js/<name>')
def js1(name=None):
    return render_template('keyAccess.js',name=name)

@app.route('/location/')
@app.route('/location/<name>')
def loc(name=None):
    return render_template('displayLocation.html', name=name)

@app.route('/location-js/')
@app.route('/location-js/<name>')
def locjs(name=None):
    return render_template('locationScript.js',name=name)


@app.route('/intent/')
@app.route('/intent/<name>')
def intentSender(name=None):
    return render_template('intentSender.html',name=name)

@app.route('/simple-javascript/')
@app.route('/simple-javascript/<name>')
def simple_javascript(name=None):
    return render_template('simpleJavascript.html', name=name)

@app.route('/content-access-js/')
def contentMain():
    return render_template('contentAccess.js')

@app.route('/BenignCookie/')
def benignCookie():
    return render_template('benignCookie.html')

@app.route('/MalCookie/')
def malCookie():
    return render_template('malCookie.html')

@app.route('/shutdown')
def shutdown():
    shutdown_server()
    return 'Server shutting down...'

#app.run('0.0.0.0', debug=True)
app.run('localhost', debug=True)
