
export CVENV=$(type "virtualenv2" > /dev/null && which virtualenv2 || which virtualenv)

$CVENV --clear --python=python2.7 .venv --prompt=\(cli\)

source .venv/bin/activate

pip install -r requirements.txt
