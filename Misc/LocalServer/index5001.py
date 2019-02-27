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
    if username in users:
        print(username)
        return users.get(username)
    return None

@app.route('/<ssn>')
@auth.login_required
def index(ssn):
    return "Hello, %s! SSN=%s" % (auth.username(), ssn)

@app.route('/test/')
@app.route('/test/<name>')
def test(name=None):
    return render_template('landingPage.html', name=name)

@app.route('/location-js/')
def locjs(name=None):
    return render_template('locationScript.js',name=name)

@app.route('/rvprasad/')
def rvprasad():
    return render_template('rvprasad.html')

@app.route('/access/')
def access():
    return render_template('access.html')

@app.route('/add/')
def add():
    return render_template('add.php')

@app.route('/demo/')
def demo():
    return render_template('demo1.html')

@app.route('/cookie1/')
def cookie1():
    return render_template('cookie1.html')

@app.route('/cookie2/')
def cookie2():
    return render_template('cookie2.html')

@app.route('/file-access-js/')
@app.route('/file-access-js/<name>')
def js(name=None):
    return render_template('fileAccess.js',name=name)


@app.route('/shutdown')
def shutdown():
    shutdown_server()
    return 'Server shutting down...'

#app.run('0.0.0.0', debug=True)
app.run(host='localhost', port=5001, debug=True)
