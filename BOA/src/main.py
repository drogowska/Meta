import matplotlib.pyplot as mlp
import numpy as np
from BOA import BOA
import pandas as pd

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

def _plot(values):
    # mlp.title("Średnie przystosowanie - " + fun )
    mlp.ylabel("Wartość przystosowania")
    mlp.xlabel("Numer iteracji")
    mlp.plot(values[0], label='size=20')
    mlp.plot(values[1], label='size=200')
    # mlp.plot(values[2], label='p=0.80')
    # mlp.plot(values[3], label='p=0.95')
    mlp.legend()
    mlp.show()

def _plot1(values1, val2, fun):
    mlp.plot(values1, label='boa')
    mlp.title("Średnie przystosowanie - " + fun)
    mlp.ylabel("Wartość przystosowania")
    mlp.xlabel("Numer iteracji")
    mlp.plot(val2, label='glpso')
    mlp.legend()
    mlp.show()

def _plot0(values1, val2, fun):
    mlp.plot(values1, label='boa')
    mlp.title("Przystosowanie cząstek w kolejnych iteracjach - " + fun )
    mlp.ylabel("Wartość przystosowania")
    mlp.xlabel("Numer iteracji")
    mlp.plot(val2, label='glpso')
    mlp.legend()
    mlp.show()


def _plot2(values, fun, alg):
    mlp.title("Średnie przystosowanie - " + alg )
    mlp.ylabel("Wartość przystosowania")
    mlp.xlabel("Numer iteracji")
    mlp.plot(values[0], label=fun[0])
    mlp.plot(values[1], label=fun[1])
    # mlp.plot(values[2], label='p=0.80')
    # mlp.plot(values[3], label='p=0.95')
    mlp.legend()
    mlp.show()

def print_tab(tab):
    for i in range(len(tab)):
        print(str(i+1) + " &" + str(tab[i]) + " \\")
    print("\n")

ranges = [[-100, 100], [-10, 10], [-100, 100]]
funs = ["SphereFuncion", "SchwefelFunction"] #, "CigarFunction"]
p = [0.4, 0.6, 0.8, 0.95]
c = [0.01, 0.2, 0.5, 0.8]
a = [0.1, 0.3, 0.5, 0.9]
boa_S = [20, 200]
dim = [15, 30]

s = [0.1, 0.01, 0.95]
sc = [0.9, 0.01, 0.95]
ass = [0.1, 0.9]
alg = ['BOA', 'GLPSO']
print("---------------------- Badanie wpływu parametrów -----------------------------")
for i in range(0,2):
    print("\nFunkcja (0 - SphereFuncion, 1 - SchwefelFunction, 2 - CigarFunction): ", i)
    print("-------------------------------------BOA---------------------------------------")


    tab = []
    pom = []

    # badanie parametrów a,p,c:
    for j in range(4):
        # cso = BOA(ranges[i][0], ranges[i][1], dim[0], i, 100, a=a[j])
        # cso = BOA(ranges[i][0], ranges[i][1], dim[0], i, 100, c=c[j])
        cso = BOA(ranges[i][0], ranges[i][1], dim[0], i, 100, p=p[j])
        res = cso._run()
        tmp = avg_fits(cso.data)
        stats(cso.data)
        tab.append(tmp)
        n = len(cso.data)-1
        pom.append(cso.data[n])

        # _plot1(tmp, funs[i], p[j])
        # _plot1(cso.data[n], funs[i], p[j])

    _plot(tab) 
    _plot(pom)    
    tab = []
    pom = []
    # bdanie wielosci populacji/wymiarowości 
    for j in range(2):

        if i == 0:
            cso = BOA(ranges[i][0], ranges[i][1], 15, i, boa_S[j], a=s[0],p=s[2], c=s[1] )
            # cso = BOA(ranges[i][0], ranges[i][1], dim[j], i, 100, a=s[0],p=s[2], c=s[1] )
        else:
            cso = BOA(ranges[i][0], ranges[i][1], 15, i, boa_S[j], a=sc[0],p=sc[2], c=sc[1] )
            # cso = BOA(ranges[i][0], ranges[i][1], dim[j], i, 100, a=sc[0],p=sc[2], c=sc[1] )

        res = cso._run()
        tmp = avg_fits(cso.data)
        stats(cso.data)

        tab.append(tmp)
        n = len(cso.data)-1
        pom.append(cso.data[n])

    _plot(tab) 
    _plot(pom)    
algs = []
boas = []
glpsos = []
print("------------------------BOA vs GLPSO ---------------------------")
for i in range(0,2):
    print("\nFunkcja (0 - SphereFuncion, 1 - SchwefelFunction, 2 - CigarFunction): ", i)
    print("-------------------------------------BOA---------------------------------------")

    cso = BOA(ranges[i][0], ranges[i][1], dim[0], i, 100, 0.95, 0.01, a=ass[i])
    res = cso._run()
    tmp = avg_fits(cso.data)
    stats(cso.data)
    boas.append(tmp)

    glpso = GLPSO(i)
    pom = []
    print("-------------------------------------GLPSO---------------------------------------")
    glpso._glpso(ranges[i][0], ranges[i][1], 15)
    pom =  avg_fits(glpso.data)
    stats(glpso.data)
    tab = []
    tab.append(pom)
    tab.append(tmp)
    glpsos.append(pom)
    _plot1(tmp, pom, funs[i])
    _plot0(cso.data[0], glpso.data[0], funs[i])
    algs.append(tab)


_plot2(boas, funs,'BOA')
_plot2(glpsos, funs,'GLPSO')




