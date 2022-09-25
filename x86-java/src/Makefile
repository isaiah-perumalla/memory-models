FLAGS = -Xbatch -XX:-TieredCompilation  -XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly -XX:PrintAssemblyOptions=intel
.SUFFIXES: .java .class

.java.class:
	javac $*.java

CLASSES = \
		  	./x86/mem/x86Test.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) ./x86/mem/*.class
	
run:
	java $(FLAGS) -cp . x86.mem.x86Test 

