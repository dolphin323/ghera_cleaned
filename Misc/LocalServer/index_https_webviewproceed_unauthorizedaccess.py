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

@app.route('/ssn/<ssn>')
@auth.login_required
def secureEntry(ssn):
    return render_template('proceed.html', username=auth.username(), ssn=ssn)

@auth.error_handler
def auth_error():
    return render_template('failed.html')

@app.route('/shutdown')
def shutdown():
    shutdown_server()
    return 'Server shutting down...'

app.run('localhost', debug=True, port=5000, ssl_context=('./keys/keyOne-publickey.crt', './keys/keyOne-privatekey.key'))
