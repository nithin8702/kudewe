package kudewe.reports.repository.serialization;

/**
 * Perform object serealization operations 
 * @author fer
 *
 * @param <Type> Object type
 * @param <Media> Serialization Media
 */
public interface Serializer<Type, Media> {
	Type DeSerealize (Media media);
}
