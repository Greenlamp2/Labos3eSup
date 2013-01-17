openssl genrsa -out ca.key 1024
openssl req -new -x509 -key ca.key -out demoCA/cacert.pem

keytool -genkey -alias client -keystore client_keystore.jks.jks
keytool -genkey -alias server -keystore serveur_keystore.jks

keytool -certreq -alias client -keystore client_keystore.jks -file clientJsse.csr -keypass password -storepass password

openssl ca -in clientJsse.csr -out clientJsse.pem -keyfile ca.key

openssl x509 -in clientJsse.pem -out clientJsse.der -outform DER

keytool -import -v -alias certifauthority -file demoCA\cacert.pem -keystore client_keystore.jks -storepass password
keytool -import -v -keystore client_keystore.jks -alias client -file clientJsse.der -storepass password

keytool -certreq -alias server -keystore serveur_keystore.jks -file serveurJsse.csr -keypass password -storepass password -v

openssl ca -in serveurJsse.csr -out serveurJsse.pem -keyfile ca.key
openssl x509 -in serveurJsse.pem -out serveurJsse.der -outform DER

keytool -import -v -alias certifauthority -file demoCA\cacert.pem -keystore serveur_keystore.jks -storepass password
keytool -import -v -keystore serveur_keystore.jks -alias server -file serveurJsse.der -storepass password


