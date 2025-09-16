Param([string]$Password='changeit')
$apps=@('bff-web','bff-mobile','bff-atm')
New-Item -ItemType Directory -Force -Path certs-out|Out-Null
foreach($app in $apps){
  & openssl req -x509 -newkey rsa:2048 -keyout "$app.key" -out "$app.crt" -days 365 -nodes -subj "/CN=localhost"
  & openssl pkcs12 -export -inkey "$app.key" -in "$app.crt" -out "$app.p12" -name "$app" -passout "pass:$Password"
  New-Item -ItemType Directory -Force -Path "../$app/src/main/resources/certs" | Out-Null
  Copy-Item "$app.p12" "../$app/src/main/resources/certs/$app.p12"
  Move-Item "$app.p12" "certs-out/$app.p12" -Force
}
