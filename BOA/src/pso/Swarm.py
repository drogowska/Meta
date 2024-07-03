from pso.Particle import Particle

class Swarm:

    def __init__(self, fun, fro, to, dim, size=100, sub=False, swarm=None):
        if sub: 
            self._swarm = swarm
            self._fun = fun
            self._swarm_size = len(swarm)
            self._winners = []

        else: 
            self._swarm = []
            self._best_particle = None
            self._winners = []
            self._avg = []
            self._swarm_size = size
            self.init(fro, to, dim, fun)


    def init(self, fro, to, dim, fun):
        for i in range(self._swarm_size):
            self._swarm.append(Particle(fro, to, dim))
            self._swarm[i]._count_fit(fun)

        # self._count_fitness(fun);
        # for i in range(self._swarm_size):
            self._swarm[i]._best_fit = self._swarm[i]._fit
        # self._best_particle = self._swarm[0]
        self.__set_best_particle()

    def _cal_avg(self):
        if len(self._swarm) != 0:
            self._avg = []
            for i in range(len(self._swarm[0]._x)):
                avg = 0
                for j in range(len(self._swarm)):
                    avg += self._swarm[j]._x[i]
                self._avg.append(avg/len(self._swarm))

    def _count_fitness(self, fun):
        for i in range(self._swarm_size):
            self._swarm[i]._count_fit(fun)

    def __set_best_particle(self):
        self._best_particle = min(self._swarm)
    
    def _update_velo(self, w, c1, ex):
        for i in range(self._swarm_size):
            self._swarm[i]._update_velo_ex(w, c1, ex)
         
    def _update_fit(self, fun):
        self._count_fitness(fun)
        for i in range(self._swarm_size):
            # self._swarm[i]._count_fit(fun)
            if self._swarm[i]._fit < self._swarm[i]._best_fit:
                self._swarm[i]._best_fit = self._swarm[i]._fit 
                self._swarm[i]._best_x = self._swarm[i]._x
        self.__set_best_particle()

    def _update_position(self, fun, w, c1, ex):
        # self._update_velo(w,c1,ex)
        for i in range(self._swarm_size):
            self._swarm[i]._update_velo_ex(w, c1, ex[i])
            self._swarm[i]._update_position()
            self._swarm[i]._update_best(fun)
        self._update_fit(fun)

    def _update_bests(self, fun):
        for i in range(self._swarm_size):
            self._swarm[i]._update_best(fun)