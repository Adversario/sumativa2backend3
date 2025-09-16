#!/usr/bin/env bash
set -euo pipefail
mkdir -p certs-out
for APP in bff-web bff-mobile bff-atm; do
  CN="localhost"
  openssl req -x509 -newkey rsa:2048 -keyout ${APP}.key -out ${APP}.crt -days 365 -nodes -subj "/CN=${CN}"
  openssl pkcs12 -export -inkey ${APP}.key -in ${APP}.crt -out ${APP}.p12 -name ${APP} -passout pass:changeit
  mkdir -p ../${APP}/src/main/resources/certs
  cp ${APP}.p12 ../${APP}/src/main/resources/certs/${APP}.p12
  mv ${APP}.p12 certs-out/${APP}.p12
done
echo "Listo. Keystores copiados"