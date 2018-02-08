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

@app.route('/loc/')
@app.route('/loc/<name>')
def loc(name=None):
    return render_template('dispLoc.html', name=name)

@app.route('/locjs/')
@app.route('/locjs/<name>')
def locjs(name=None):
    return render_template('locScripts.js',name=name)


@app.route('/intent/')
@app.route('/intent/<name>')
def intentSender(name=None):
    return render_template('intentSender.html',name=name)

app.run('0.0.0.0', debug=True)
