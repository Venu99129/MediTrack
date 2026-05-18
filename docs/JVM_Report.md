# JVM (JAVA VIRTUAL MACHINE) REPORT

JVM is a virtual machine that enables a computer to run Java programs as well as programs written in other languages that are also compiled to Java bytecode. The JVM is an abstract computing machine that enables a computer to run a Java program by converting Java bytecode into machine code.

---

## JVM Architecture

### Class Loader Subsystem

The class loader subsystem is responsible for loading classes into the JVM. It loads classes from the file system, network, or other sources. The class loader subsystem is divided into three parts: 
#### Bootstrap class loader
the bootstrap class loader is responsible for loading the core Java classes that are essential for the JVM to function. It loads classes from the Java Runtime Environment (JRE) and is implemented in native code.

#### Extension class loader,
the extension class loader is responsible for loading classes from the extension directories. It loads classes that are not part of the core Java classes but are still essential for the JVM to function. It is implemented in Java and is a child of the bootstrap class loader.

#### Application class loader.
the application class loader is responsible for loading classes from the application's classpath. It loads classes that are specific to the application and is implemented in Java. It is a child of the extension class loader.

---

### Runtime Data Areas
The JVM has several runtime data areas that are used to store information about the program being executed.
- **Method Area**: This area stores class-level data such as class definitions
- **Heap**: This area is used for dynamic memory allocation and stores objects created during the execution of the program.
- **Stack**: This area is used for storing method frames, which contain local variables and
- **Program Counter (PC) Register**: This register holds the address of the currently executing instruction.
- **Native Method Stack**: This area is used for executing native methods, which are methods

---

### Execution Engine
The execution engine is responsible for executing the bytecode instructions. It consists of the following components:
- **Interpreter**: This component executes bytecode instructions one at a time. It is simple
- **Just-In-Time (JIT) Compiler**: This component compiles bytecode into native machine code for improved performance. It is more complex than the interpreter but can significantly improve the performance of the program.
- **Garbage Collector**: This component is responsible for automatically managing memory by reclaiming memory

### JIT Compiler vs Interpreter
The JIT compiler and interpreter are two different approaches to executing Java bytecode. The interpreter executes bytecode instructions one at a time, while the JIT compiler compiles bytecode into native machine code for improved performance.

The interpreter is simple and easy to implement, but it can be slow for complex programs. The JIT compiler is more complex but can significantly improve the performance of the program by compiling bytecode into native machine code.

---

### Write Once, Run Anywhere
The JVM allows Java programs to be written once and run anywhere. This is because the JVM abstracts away the underlying hardware and operating system, allowing Java programs to run on any platform that has a compatible JVM implementation. This is one of the key advantages of Java and has contributed to its widespread adoption in various applications and industries.

---

### Conclusion
The JVM is a powerful and versatile virtual machine that enables Java programs to run on a wide range of platforms. Its architecture, including the class loader subsystem, runtime data areas, and execution engine, allows for efficient execution of Java bytecode and provides features such as automatic memory management and platform independence.