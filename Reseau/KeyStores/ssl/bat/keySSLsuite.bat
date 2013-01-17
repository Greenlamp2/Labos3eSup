keytool -certreq -alias server -keystore serveur_keystore -file serveurJsse.csr -keypass password -storepass password -v

openssl ca -in serveurJsse.csr -out serveurJsse.pem -keyfile ca.key
openssl x509 -in serveurJsse.pem -out serveurJsse.der -outform DER

keytool -import -v -alias certifauthority -file demoCA\cacert.pem -keystore serveur_keystore -storepass password
keytool -import -v -keystore serveur_keystore -alias server -file serveurJsse.der -storepass password
