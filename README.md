![alt text](<LOGO.jpg>)
# INFORME DEL PROYECTO: LABERINTO RESOLVER

## Informacion General
**Universidad Politécnica Salesiana (UPS)**  
Carrera de Computación  
Materia: Estructura de Datos - Grupo 1  

### Integrantes:
- **Andres Renato Yadaicela Uyaguari**  
  Correo Institucional: [ayadaicela@est.ups.edu.ec](mailto:ayadaicela@est.ups.edu.ec)  
- **Franklin Nicolas Pomaquiza Guaman**  
  Correo Institucional: [fpomaquizag@est.ups.edu.ec](mailto:fpomaquizag@est.ups.edu.ec)  

Profesor: Ingeniero Pablo Torres  

---

## Descripción del Problema
Este proyecto consiste en una aplicación desarrollada en Java que permite resolver laberintos utilizando diferentes algoritmos de búsqueda como BFS (Breadth-First Search), DFS (Depth-First Search), Recursivo y Programación Dinámica con caché. La aplicación cuenta con una interfaz gráfica intuitiva donde los usuarios pueden definir un laberinto, establecer puntos de inicio y fin, y seleccionar el algoritmo que desean utilizar para encontrar el camino más corto.

El objetivo principal es proporcionar una herramienta educativa y funcional que permita explorar las diferencias entre los distintos algoritmos de búsqueda y su eficiencia en la resolución de problemas de laberintos.

---
## Herramientas y Lenguajes Utilizados

### **Lenguaje de Programación**
- **Java**:  
  El proyecto está desarrollado completamente en Java, un lenguaje orientado a objetos ampliamente utilizado para aplicaciones de escritorio y sistemas complejos. Se aprovechan características como herencia, interfaces, colecciones y programación multihilo para implementar los algoritmos y la interfaz gráfica.

---

### **Biblioteca de Interfaz Gráfica**
- **Swing**:  
  Se utiliza la biblioteca Swing para crear la interfaz gráfica de usuario (GUI). Esta herramienta permite diseñar una interfaz interactiva donde los usuarios pueden configurar laberintos, seleccionar algoritmos y visualizar los resultados. Componentes como `JPanel`, `JButton`, `JTextField` y `JLabel` son empleados para construir la interfaz.

---

### **Algoritmos Implementados**

1. **Método Recursivo**:
   - Implementado en la clase `MazeSolverRecursivo`.
   - Utiliza recursión para explorar todas las posibles rutas en el laberinto.
   - Es útil para problemas pequeños o medianos, pero puede ser ineficiente para laberintos grandes debido al alto consumo de memoria.

2. **Programación Dinámica con Caché**:
   - Implementado en la clase `MazeSolverCache`.
   - Almacena resultados intermedios en una estructura de caché (`Map<Cell, Integer>`) para evitar cálculos redundantes.
   - Mejora significativamente la eficiencia en laberintos grandes.

3. **BFS (Breadth-First Search)**:
   - Implementado en la clase `MazeSolverBFS`.
   - Explora todos los nodos vecinos antes de avanzar a niveles más profundos.
   - Garantiza encontrar el camino más corto en grafos no ponderados.

4. **DFS (Depth-First Search)**:
   - Implementado en la clase `MazeSolverDFS`.
   - Explora profundamente un camino hasta llegar al final o encontrar un obstáculo.
   - No garantiza encontrar el camino más corto, pero es eficiente en términos de memoria.

5. **Método Extra**:
   - Implementado en la clase `MazeSolverExtra`.
   - Encuentra todos los caminos posibles en el laberinto, resaltando el camino óptimo en amarillo y otros caminos alternativos en verde.
   - Útil para análisis exhaustivos o simulaciones avanzadas.

---

### **Estructuras de Datos Utilizadas**
- **Matrices (`boolean[][] grid`)**: Representan el laberinto, donde `true` indica una celda transitable y `false` una pared.
- **Listas (`List<Cell>`)**: Almacenan los caminos encontrados por los algoritmos.
- **Colas (`Queue<Cell>`)**: Utilizadas en BFS para explorar nodos nivel por nivel.
- **Conjuntos (`Set<Cell>`)**: Empleados para evitar visitar celdas repetidas en DFS y métodos recursivos.
- **Mapas (`Map<Cell, Cell>` y `Map<Cell, Integer>`)**: Utilizados para almacenar relaciones entre celdas (padres-hijos) y cachés de resultados intermedios.

---

### **Entorno de Desarrollo**
- **IDE**: Visual Studio Code  
  Este entorno de desarrollo ofrece herramientas avanzadas para depurar, ejecutar y gestionar proyectos Java.
- **Control de Versiones**: Git  
  Se utiliza para gestionar el código fuente y facilitar la colaboración entre los integrantes del equipo.

---

### **Animaciones y Visualización**
- Las animaciones se implementan utilizando `Thread.sleep()` para simular retardos durante la visualización de los caminos encontrados.
- Los colores se utilizan estratégicamente para diferenciar entre:
  - Camino óptimo (verde para BFS, cian para DFS, azul para recursivo, magenta para caché, amarillo para el método Extra).
  - Paredes (`Color.BLACK`).
  - Celdas transitables (`Color.WHITE`).
  - Punto de inicio y fin (`ORANGE_BRIGHT`).

---

## Propuesta de Solución

### Marco Teórico

#### **Programación Dinámica**
La programación dinámica es una técnica utilizada para resolver problemas complejos dividiéndolos en subproblemas más pequeños. Al almacenar soluciones intermedias en una estructura de datos (como un caché), se evita la recalculación de resultados previamente obtenidos, lo que mejora significativamente la eficiencia.

#### **BFS (Breadth-First Search)**
El algoritmo BFS explora todos los nodos vecinos de un nodo inicial antes de avanzar a niveles más profundos. Es especialmente útil para encontrar el camino más corto en grafos no ponderados, ya que garantiza que el primer camino encontrado es el óptimo.

#### **DFS (Depth-First Search)**
El algoritmo DFS explora profundamente un camino hasta llegar al final o encontrar un obstáculo antes de retroceder. Aunque es eficiente en términos de memoria, no garantiza encontrar el camino más corto.

#### **Recursión**
La recursión es una técnica en la que una función se llama a sí misma para resolver subproblemas. Es útil para problemas pequeños o medianos, pero puede ser ineficiente para laberintos grandes debido al alto consumo de memoria y riesgo de desbordamiento de pila.

---

### Descripción de la Propuesta de Solución
La solución implementada consta de una aplicación Java que incluye:
1. **Interfaz Gráfica (Swing):**
   - Permite configurar el tamaño del laberinto.
   - Los usuarios pueden definir paredes, puntos de inicio y fin mediante clics.
   - Seleccionar entre diferentes algoritmos de búsqueda para resolver el laberinto.

2. **Algoritmos Implementados:**
   - **MazeSolverBFS:** Encuentra el camino más corto utilizando BFS.
   - **MazeSolverDFS:** Explora todas las rutas posibles utilizando DFS.
   - **MazeSolverRecursivo:** Utiliza recursión para encontrar caminos.
   - **MazeSolverCache:** Implementa programación dinámica con caché para mejorar la eficiencia.
   - **MazeSolverExtra:** Encuentra todos los caminos posibles en el laberinto.

3. **Animaciones y Visualización:**
   - Los caminos encontrados se muestran en la interfaz gráfica con animaciones opcionales.
   - Las métricas de rendimiento (tiempo y pasos) se muestran al finalizar la ejecución.

---

## Criterios por Integrante

### **Andres Renato Yadaicela Uyaguari**
- **Contribuciones:**
  - Implementación de la interfaz gráfica utilizando Swing.
  - Diseño y desarrollo de la clase `MazeSolverUI` para integrar todos los componentes visuales.
  - Implementación de los algoritmos BFS y DFS.
- **Responsabilidad:**
  - Garantizar la funcionalidad y usabilidad de la interfaz gráfica.
  - Documentar y explicar el funcionamiento de los algoritmos BFS y DFS.
- **Conclusión Personal:**
  - BFS es la mejor opción para encontrar el camino más corto debido a su capacidad para garantizar que el primer camino encontrado es el óptimo. Sin embargo, consume más memoria que DFS.
  - Este proyecto podría extenderse para crear simulaciones de rutas en videojuegos o aplicaciones de navegación.

---

### **Franklin Nicolas Pomaquiza Guaman**
- **Contribuciones:**
  - Implementación de los algoritmos recursivos y con caché.
  - Diseño y desarrollo de la clase `MazeSolverCache` para optimizar la búsqueda utilizando programación dinámica.
  - Pruebas y validación de la funcionalidad de los algoritmos.
- **Responsabilidad:**
  - Optimizar el rendimiento de los algoritmos mediante técnicas como el uso de caché.
  - Documentar y explicar el funcionamiento de los algoritmos recursivos y con caché.
- **Conclusión Personal:**
  - La programación dinámica con caché es una solución eficiente para laberintos grandes, ya que reduce significativamente el tiempo de ejecución al evitar cálculos redundantes.
  - Este proyecto podría aplicarse en la creación de juegos de laberintos o simuladores de rutas para entrenamiento.

---

## Capturas de la Implementación Final

### Interfaz Gráfica
![Interfaz Principal](<INTERFAZ.jpeg>)
*Descripción: Interfaz principal de la aplicación con opciones para configurar el laberinto y seleccionar algoritmos. Los usuarios pueden definir paredes (celdas no transitables), puntos de inicio y fin mediante clics derechos e izquierdos. Además, pueden elegir entre diferentes algoritmos como BFS, DFS, Recursivo, Cache y Extra desde botones claramente etiquetados.*

### Resultado de BFS
![Resultado BFS](<BFS.jpeg>)
*Descripción: Visualización del camino más corto encontrado utilizando el algoritmo BFS (Breadth-First Search). El camino se resalta en verde, mostrando cómo el algoritmo explora todas las celdas nivel por nivel hasta encontrar el destino. Este método garantiza que el primer camino encontrado sea el más corto.*

### Resultado de DFS
![Resultado DFS](<DFS.jpeg>)
*Descripción: Visualización de un camino encontrado utilizando el algoritmo DFS (Depth-First Search). El camino se resalta en cian, mostrando cómo el algoritmo explora profundamente un camino antes de retroceder. Aunque encuentra un camino, no garantiza que sea el más corto.*

### Resultado de Recursivo
![Resultado Recursivo](<RECUR.jpeg>)
*Descripción: Visualización del camino encontrado utilizando el método recursivo. El camino se resalta en azul, mostrando cómo el algoritmo utiliza llamadas recursivas para explorar todas las posibles rutas. Este método es útil para laberintos pequeños, pero puede ser ineficiente para laberintos grandes debido al alto consumo de memoria y riesgo de desbordamiento de pila.*

### Resultado de Cache
![Resultado Cache](<CACHE.jpeg>)
*Descripción: Visualización del camino encontrado utilizando programación dinámica con caché. El camino se resalta en magenta, mostrando cómo el algoritmo reutiliza resultados previamente calculados para mejorar la eficiencia. Este método es especialmente útil para laberintos grandes, ya que reduce significativamente el tiempo de ejecución al evitar cálculos redundantes.*

### Resultado de Extra
![Resultado Extra](<EXTR.jpeg>)
*Descripción: Visualización de todos los caminos posibles encontrados utilizando el método Extra. Los caminos óptimos se resaltan en amarillo, mientras que otros caminos alternativos se muestran en verde. Este método permite explorar todas las rutas posibles en el laberinto, lo que puede ser útil para análisis exhaustivos o simulaciones avanzadas.*

---

## Conclusiones

1. **Mejor Opción para Encontrar el Camino Más Corto:**
   - El algoritmo **BFS** es la mejor opción para encontrar el camino más corto en un laberinto o grafo no ponderado. A pesar de su mayor consumo de memoria, garantiza que el primer camino encontrado es el óptimo.

2. **Eficiencia de los Algoritmos:**
   - **DFS** es útil para explorar completamente un laberinto, pero no garantiza encontrar el camino más corto.
   - **Programación Dinámica con Caché** ofrece un buen equilibrio entre tiempo de ejecución y uso de memoria, siendo ideal para laberintos grandes.
   - **Recursión** es adecuada para laberintos pequeños, pero ineficiente para problemas complejos.

3. **Aplicaciones Futuras:**
   - Este proyecto podría extenderse para desarrollar juegos de laberintos o simuladores de rutas en aplicaciones educativas y de entrenamiento.

---

## Consideraciones Finales

### Por Andres Renato Yadaicela Uyaguari:
- Podríamos agregar la capacidad de generar laberintos aleatorios para aumentar la rejugabilidad.
- Aplicar esta solución en videojuegos de estrategia para simular rutas óptimas.

### Por Franklin Nicolas Pomaquiza Guaman:
- Implementar algoritmos adicionales como A* para comparar su rendimiento con BFS y DFS.
- Extender el proyecto para soportar laberintos tridimensionales.

---

## Instrucciones para Ejecutar el Proyecto

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/AURUX909/ICC-EST-U2-PROYECTO-FINAL-ESTRU.git
   ```
2. Abrir el proyecto en un IDE compatible con Java (Eclipse, IntelliJ IDEA, Visual Studio).
3. Ejecutar la clase principal `MazeSolverUI.java` para iniciar la aplicación grafica.
4. Configurar el laberinto y seleccionar el algoritmo deseado desde la interfaz gráfica.

---
