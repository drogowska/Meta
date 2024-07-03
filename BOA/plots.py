import matplotlib.pyplot as mlp
import numpy as np
from BOA import BOA
from pso.GLPSO import GLPSO

def avg_fits(values):
    res = []
    for i in range(len(values[0])):
        avg = 0
        for j in range(len(values)):
            avg += values[j][i]
        res.append(avg/len(values))
    return res

def stats(values):
    res = avg_fits(values)
    print("Średnia najmniejsza wartość przystosowania: ", min(res))
    a = min(res)
    print("Średnia największa wartość przystosowania:  ", max(res))
    b = max(res)
    print("Średnia wartość przystosowania:             ", sum(res)/len(res))
    c = sum(res)/len(res)
    print("Odchylenie standardowe:                     ", np.std(res))
    # print(str(round(a,3)) + " & " + str(round(max(res),3)) + " & " + str(round(sum(res)/len(res),3)) + " & " +str(round(np.std(res),3)) + " \\ ")

def _plot(values, fun):
    mlp.title("Średnie przystosowanie - " + fun )
    mlp.ylabel("Wartość przystosowania")
    mlp.xlabel("Numer iteracji")
    mlp.plot(values[0], label='SphereFuncion')
    mlp.plot(values[1], label='SchwefelFunction')
    mlp.plot(values[2], label='CigarFunction')
    mlp.legend()
    mlp.show()

def _plot1(values1, val2, fun):
    mlp.plot(values1, label='cso')
    mlp.title("Średnie przystosowanie - " + fun )
    mlp.ylabel("Wartość przystosowania")
    mlp.xlabel("Numer iteracji")
    mlp.plot(val2, label='lsco')
    mlp.legend()
    mlp.show()

def _plot0(values1, val2, fun):
    mlp.plot(values1, label='cso')
    mlp.title("Przystosowanie cząstek w kolejnych iteracjach - " + fun )
    mlp.ylabel("Wartość przystosowania")
    mlp.xlabel("Numer iteracji")
    mlp.plot(val2, label='lsco')
    mlp.legend()
    mlp.show()


def print_tab(tab):
    for i in range(len(tab)):
        print(str(i+1) + " &" + str(tab[i]) + " \\")
    print("\n")

ranges = [[-100, 100], [-10, 10], [-100, 100]]
funs = ["SphereFuncion", "SchwefelFunction", "CigarFunction"]
csos = []
lscos = []
sizes = []


p = [0.1, 0.5, 0.8, 0.95]
c = [0.01, 0.2, 0.5, 0.8]
a = [0.1, 0.3, 0.5, 0.9]


boa_S = [20, 50]
dim = [15, 20]

for i in range(2):
    print("\nFunkcja (0 - SphereFuncion, 1 - SchwefelFunction, 2 - CigarFunction): ", i)
    # print("----------------------------CSO-----------------------------")
    # cso._run()
    # tmp = avg_fits(cso._data)
    # csos.append(tmp)
    # stats(cso._data)
    # n = len(cso._data)

    # for n in range(2):
    #     for j in range(2):
    #         cso = BOA(i, ranges[i][0], ranges[i][1], dim[n], boa_S[j])
    #         cso._boa()

    # for k in range(4):
    #     for l in range(4):


    glpso = GLPSO(i)

    pom = []
    # glpso = GLPSO(w, c1, c2, i)
    # print("----------------------------LCSO-------------------------------")
    # glpso._run(ranges[i][0], ranges[i][1], 15)
    glpso._glpso(ranges[i][0], ranges[i][1], 15)

            
    # pom =  avg_fits(lcso._data)
    # lscos.append(pom)
    # stats(lcso._data)

    # _plot1(tmp, pom, funs[i])
    # _plot0(cso._data[0], lcso._data[0], funs[i])

_plot(csos, "COS")
_plot(lscos, "LCOS")

# for i in csos:
#     print_tab(i)

# for i in lscos:
#     print_tab(i)





