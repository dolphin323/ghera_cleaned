from flask import render_template
from flask import Flask
from flask import request
app = Flask(__name__)
#app.config['SERVER_NAME'] = 'mymac:5000'
#app.run('0.0.0.0', debug=True, port=5000, ssl_context=('/Users/Joy/ssl/certificates/server.crt', '/Users/Joy/ssl/certificates/myKey'))
#app.run('0.0.0.0', debug=True)

def shutdown_server():
    func = request.environ.get('werkzeug.server.shutdown')
    if func is None:
        raise RuntimeError('Not running serever')
    func()

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

@app.route('/shutdown', methods=['POST'])
def shutdown():
    shutdown_server()
    return 'Server shutting down...'

app.run('0.0.0.0', debug=True)
