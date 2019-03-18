# Pre-requisites

1.  Python 2.6 or Python3.3 or higher

# Setup HTTP Local Server

1.  Install Flask

    `$ pip install Flask`

2.  Run the http server

    `$ python index_http.py`

    Open up a web browser and type http://localhost:5000/test/

# Setup HTTPS Local Server

1.  Install Flask

    `$ pip install Flask`

2.  Run the https server

    `$ python index_https.py`

    Open up a web browser and type https://localhost:5000/test/

# Setup HTTPS Local Server with Authorization
1.  Install Flask

    `$ pip install Flask`

2. Install Flask-HTTPAuth

    `$ pip install Flask-HTTPAuth`

3.  Run the https server with authorization

    `$ python index_https_webviewproceed_unauthorizedaccess.py`

# Setup self-signed Certificates

The certificates in the ssl/certificates folder were created with OpenSSL on a Mac. If these do not work then you can create your own certificates. Follow the instructions [here](https://support.rackspace.com/how-to/generate-a-csr-with-openssl/) for creating self-signed certificates with OpenSSL.
