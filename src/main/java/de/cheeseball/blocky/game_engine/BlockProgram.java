package de.cheeseball.blocky.game_engine;

import static org.lwjgl.opengl.GL43C.*;


public class BlockProgram{

    private int program;

    //pacakge private Constructor, just gameEngine should be able to instantiate
    //also we should make this sigleton? => TODO make singleton
    BlockProgram(){

        program = glCreateProgram();

        float vertices[] = {
            1.0f,	1.0f,	1.0f,1.0f,
            0.0f,	1.0f,	1.0f,1.0f,
            1.0f,	1.0f,	0.0f,1.0f,
            0.0f,	1.0f,	0.0f,1.0f,
            1.0f,	0.0f,	1.0f,1.0f,
            0.0f,	0.0f,	1.0f,1.0f,
            0.0f,	0.0f,	0.0f,1.0f,
            1.0f,	0.0f,	0.0f,1.0f
        };

        int indices[] = {
            3, 2, 6, 7, 4, 2, 0,
            3, 1, 6, 5, 4, 1, 0
        };

        float colors[] = {
            1.0f,0.0f,0.0f,1.0f,
            0.0f,1.0f,0.0f,1.0f,
            0.0f,0.0f,1.0f,1.0f,
            1.0f,1.0f,0.0f,1.0f,
            1.0f,0.0f,1.0f,1.0f,
            0.0f,1.0f,1.0f,1.0f,
            1.0f,0.0f,0.0f,1.0f,
            0.0f,1.0f,0.0f,1.0f
        };

        //create VertexBuffer
        int vertexBuffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
         
        //create IndexBuffer
        int indexBuffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, indexBuffer);
        glBufferData(GL_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        //create ColorBuffer
        int colorBuffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, colorBuffer);
        glBufferData(GL_ARRAY_BUFFER, colors, GL_STATIC_DRAW);

        //VertexShader
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader,
            "attribute vec4 verts;              \n"+    //TODO make file for shader
            "attribute vec4 aColor;             \n"+
            "varying vec4 vColor;               \n"+
            "uniform mat4 trans;                \n"+
            "void main() {                      \n"+
            "   vColor      = aColor;           \n"+
            "   gl_Position = trans * verts;    \n"+
            "};                                 \n");
        glCompileShader(vertexShader);
    
        //FragmentShader
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader,
            "precision mediump float;               \n"+
            "varying vec4 vColor;                   \n"+
            "void main() {                          \n"+
            "  gl_FragColor = vec4(1.0)                \n"+
            "};                                     \n");
        glCompileShader(fragmentShader);
        
        //Attach Shaders
        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);
        
        //Compile Program
        glLinkProgram(program);

        //Set Positions here once they does not change
        int pos = glGetAttribLocation(program, "verts");
        glEnableVertexAttribArray(pos);
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
        glVertexAttribPointer(pos,4,GL_FLOAT,false,0,0L);

        //Set Colors here once they does not change
        int col = glGetAttribLocation(program, "aColor");
        glEnableVertexAttribArray(col);
        glBindBuffer(GL_ARRAY_BUFFER, colorBuffer);
        glVertexAttribPointer(col,4,GL_FLOAT,false,0,0L);

    }

    int getProgam(){
        return program;
    }


}