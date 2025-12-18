package jobs4u.base.applicationmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.util.StringPredicates;
import jakarta.persistence.Embeddable;

@Embeddable
public class ApplicationAttachedFile implements ValueObject, Comparable<ApplicationAttachedFile>{


    private String fileRef;



    public ApplicationAttachedFile(String fileRef) {
        if (StringPredicates.isNullOrEmpty(fileRef)) {
            throw new IllegalArgumentException(
                    "The application email should not be empty or null");
        }

        this.fileRef = fileRef;
    }

    public ApplicationAttachedFile() {

    }



    @Override
    public String toString() {
        return fileRef;
    }

    @Override
    public int compareTo(ApplicationAttachedFile o) {
        return this.fileRef.compareTo(o.fileRef);
    }

	public void addFile(String file) {
		StringBuilder built = new StringBuilder();
		built.append(fileRef).append(file).append("\n");
		this.fileRef = built.toString();
	}

    public void removeFile(String file){
        this.fileRef = this.fileRef.replace(file+"\n","");
    }
}
