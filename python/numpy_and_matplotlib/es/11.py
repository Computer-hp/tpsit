nomi = ('GIUSEPPE', 'GABRIELE', 'SILVIO LAFAYETTE', 'GIOVANNI', 'NICOLA', 'MATTIA',
         'FILIPPO', 'MOHAMMED', 'JACOPO', 'GIORGIO', 'MILAN', 'JACKSON PETER', 'NICOLA',
         'SARA', 'GIULIO', 'LUCA', 'GIORGIO', 'MANUEL', 'ANDREA', 'ALESSANDRO', 'ALBERTO', 'MARCO')
cognomi = ('ANASTRELLI', 'ANSALDI', 'BARAZZONI', 'BASSO', 'BERTOLDI', 'CAVALIERE',
           'CUCINIELLO', 'EL BAHI', 'FANTIN', 'FOTOUDIS', 'GITARIC', 'JACOBI', 'MASTROTTO',
           'MENEGUZZO', 'MORACCHIATO', 'NENCIONI', 'POZZA', 'RANCAN', 'RIGOBELLO', 'TIBALDO',
           'VALENTE', 'ZACCARIA')

lista_di_dizionari = [{'nome': nome, 'cognome': cognome} for nome, cognome in zip(nomi, cognomi)]
print(lista_di_dizionari)