package de.cheeseball.blocky.game_engine;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL43C.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;


import javax.vecmath.Matrix4f;

public class Render{
    // The window handle
	private long window;

	private Matrix4f rota;
	private float[] v;
	private int trans;
	private float angle;

	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		init();
		make();
		loop();

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	private void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(300, 300, "Hello World!", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);


		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();
	}

	private void loop() {
		
		// Set the clear color
		glClearColor(1.0f, 1.0f, 0.0f, 0.0f);
		glEnable(GL_DEPTH_TEST);
		
		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while ( !glfwWindowShouldClose(window) ) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

			draw();

			glfwSwapBuffers(window); // swap the color buffers

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
		}
	}

	private void draw(){
		rota.rotX(angle);
		rota.rotY(angle);
		
		angle +=0.01;

		v[0] = rota.m00;
		v[1] = rota.m10;
		v[2] = rota.m20;
		v[3] = rota.m30;
		v[4] = rota.m01;
		v[5] = rota.m11;
		v[6] = rota.m21; 
		v[7] = rota.m31;
		v[8] = rota.m02;
		v[9] = rota.m12;
		v[10]= rota.m22;
		v[11]= rota.m32;
		v[12]= rota.m03;
		v[13]= rota.m13;
		v[14]= rota.m23;
		v[15]= rota.m33;
		

		glUniformMatrix4fv(trans,false , v);

		glDrawArrays(GL_TRIANGLE_STRIP,0,14);
	}

	private void make(){
		
		// did not read it yet, so TODO but its says most efficient way and gives an example ;)
		// https://www.paridebroggi.com/blogpost/2015/06/16/optimized-cube-opengl-triangle-strip/

		angle = 0f;
		
		BlockProgram prog = new BlockProgram();
		glUseProgram(prog.getProgam());
		
		rota = new Matrix4f();
		rota.setIdentity();
		rota.rotX(4);
		
		trans = glGetUniformLocation(prog.getProgam(), "trans");
		v = new float[] {rota.m00, rota.m10, rota.m20, rota.m30,
			rota.m01, rota.m11, rota.m21, rota.m31,
			rota.m02, rota.m12, rota.m22, rota.m32,
			rota.m03, rota.m13, rota.m23, rota.m33};


		glUniformMatrix4fv(trans,false , v);

}

}