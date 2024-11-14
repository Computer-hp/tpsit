import numpy as np
import matplotlib.pyplot as plt

def trova_valore_piu_frequente(array):
	array_n_unici, array_occorenze_numeri = np.unique(array, return_counts=True)
	indice_val_piu_frequente = np.argmax(array_occorenze_numeri)
	print(array_n_unici[indice_val_piu_frequente])

trova_valore_piu_frequente(np.array([1, 2, 2, 3, 4, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6]))