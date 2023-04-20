package in.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.CoursesEntity;

public interface CoursesRepo extends JpaRepository<CoursesEntity, Integer>{

}
