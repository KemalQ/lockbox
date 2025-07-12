package lockBox.dao;

import lockBox.entity.BinaryContent;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.AbstractDocument;

public interface BinaryContentDAO extends JpaRepository<BinaryContent, Long> {
}
