from flask import render_template
from flask import Flask
app = Flask(__name__)
#app.config['SERVER_NAME'] = 'mymac:5000'
#app.run('0.0.0.0', debug=True, port=5000, ssl_context=('/Users/Joy/ssl/certificates/server.crt', '/Users/Joy/ssl/certificates/myKey'))
#app.run('0.0.0.0', debug=True)

@app.route('/test/')
@app.route('/test/<name>')
def test(name=None):
    return render_template('demo1.html', name=name)

@app.route('/index/')
@app.route('/index/<name>')
def index(name=None):
    return render_template('index.html', name=name)

@app.route('/file-access-js/')
@app.route('/file-access-js/<name>')
def js(name=None):
    return render_template('fileAccess.js',name=name)

@app.route('/loc/')
@app.route('/loc/<name>')
def loc(name=None):
    return render_template('dispLoc.html', name=name)

app.run('0.0.0.0', debug=True, port=5000, ssl_context=('./ssl/certificates/server.crt', './ssl/certificates/myKey'))
