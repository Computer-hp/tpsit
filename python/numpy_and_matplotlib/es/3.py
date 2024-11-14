import numpy as np

def trova_val_piu_frequente(array):
	array_numeri_singoli, array_conteggio_occorrenze_numeri = np.unique(array, return_counts = True)
	indice_n_con_piu_occorrenze = np.argmax(array_conteggio_occorrenze_numeri)
	return array_numeri_singoli[indice_n_con_piu_occorrenze]