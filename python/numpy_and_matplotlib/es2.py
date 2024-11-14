import numpy as np
import matplotlib.pyplot as plt

L = 10
bersaglio = np.zeros((L, L), dtype = int)

for layer in range((L + 1) // 2):
	value = layer + 1
	bersaglio[layer:L - layer, layer] = value
	bersaglio[layer, layer:L - layer] = value
	bersaglio[layer:L - layer, L - layer - 1] = value
	bersaglio[L - layer - 1, layer:L - layer] = value

plt.imshow(bersaglio, cmap = "binary", origin = "lower")
plt.show()