package microserviesplatform.microservicio_cursos.eventlistener;

import microserviesplatform.microservicio_cursos.models.CursoUsuario;
import microserviesplatform.microservicio_cursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PreRemove;

//ESTA ES OTRA FORMA DE UTILIZAR LOS ENTITY LISTENER COMO EL PREREMOVE , PREUPDATE , PREPERSIST Y HAY QUE ANOTAR EL ENTITY CON @ENTITYLISTENER(ESTACLASE.CLASS)
//EL PROBLEMA ES QUE NO PUEDES USAR BEANS AQUI ADENTRO COMO LOS AUTOWIRED ETC.
/*public class ListenEventCursoUsuario {
    @Autowired
    private CursoRepository cursoRepository;

    @PreRemove
    public void preRemoveCursoUsuario(CursoUsuario cursoUsuario){
        cursoUsuario.getCursos().forEach(curso -> cursoRepository.findById(curso.getId()).get().getCursoUsuarios().remove(cursoUsuario));
    }
}*/
