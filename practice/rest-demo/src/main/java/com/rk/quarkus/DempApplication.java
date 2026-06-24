package com.rk.quarkus;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class DempApplication {
	
	public static void main(String[] args) {
		Quarkus.run(args);
	}
}
