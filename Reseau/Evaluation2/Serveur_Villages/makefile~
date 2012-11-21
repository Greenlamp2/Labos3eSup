OBJS=mainServer.cpp NetworkServer.o PoolThread.o FHMP.o
OBJC=mainClient.cpp NetworkClient.o
CC=c++ -DLIN -g
DEPRE=-Wno-Deprecated

all:			Serveur_Villages Applic_Materiel

Serveur_Villages:	$(OBJS)
			@echo "Creation de Serveur_Villages"
			@$(CC) -o Serveur_Villages $(OBJS) $(DEPRE)
			
Applic_Materiel:	$(OBJC)
			@echo "Creation de Applic_Materiel"
			@$(CC) -o Applic_Materiel $(OBJC) $(DEPRE)
			
NetworkServer.o:	NetworkServer.cpp NetworkServer.h
			@echo "Creation de NetworkServer.o"
			@$(CC) -c NetworkServer.cpp $(DEPRE)
			
NetworkClient.o:	NetworkClient.cpp NetworkClient.h
			@echo "Creation de NetworkClient.o"
			@$(CC) -c NetworkClient.cpp $(DEPRE)
			
PoolThread.o:		PoolThread.cpp PoolThread.h
			@echo "Creation de PoolThread.o"
			@$(CC) -c PoolThread.cpp $(DEPRE)
			
FHMP.o:			FHMP.cpp FHMP.h
			@echo "Creation de FHMP.o"
			@$(CC) -c FHMP.cpp $(DEPRE)
			

clean:
	rm ./*.o
	rm ./Serveur_Villages
	rm ./*.~