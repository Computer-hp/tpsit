import numpy as np
import matplotlib.pyplot as plt

L = 10
bersaglio = np.zeros((L, L), dtype = int)

for i in range((L + 1) // 2):
	value = i + 1
	bersaglio[i : L - i, i] = value
	bersaglio[i, i : L - i] = value
	bersaglio[i : L - i, L - i - 1] = value
	bersaglio[L - i - 1, i : L - i] = value

plt.imshow(bersaglio, cmap = "binary")
plt.show()