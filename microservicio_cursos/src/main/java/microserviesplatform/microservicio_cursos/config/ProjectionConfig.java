package microserviesplatform.microservicio_cursos.config;

import microserviesplatform.microservicio_cursos.models.Curso;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

//ESTO SE USA SI TUS PROJECTIONS NO ESTAN EN EL MISMO PACKAGE QUE LAS DE TUS MODELS, SI NO NO TIENES QUE HACER NADA DE CONFIGURACION PA LAS PROJECTION
/*@Configuration
public class ProjectionConfig implements RepositoryRestConfigurer {

        @Override
        public void configureRepositoryRestConfiguration(
                RepositoryRestConfiguration repositoryRestConfiguration, CorsRegistry cors) {
            repositoryRestConfiguration.getProjectionConfiguration()
                    .addProjection(Curso.class);
        }
    }*/

