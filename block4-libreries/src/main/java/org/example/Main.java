package org.example;

public class Main {
    public static void main(String[] args) {
        Persona p=new Persona();
        p.setDni("134");
        p.setName("daniel");
        PersonaDTO pDTO=PersonMapper.INSTANCE.toPersonDTO(p);
        System.out.println(pDTO.getName() + " " + pDTO.getDni());
    }
}