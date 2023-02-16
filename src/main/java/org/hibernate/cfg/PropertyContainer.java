//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.hibernate.cfg;

import jakarta.persistence.*;
import org.hibernate.AnnotationException;
import org.hibernate.annotations.*;
import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.annotations.common.reflection.XProperty;
import org.hibernate.boot.MappingException;
import org.hibernate.boot.jaxb.Origin;
import org.hibernate.boot.jaxb.SourceType;
import org.hibernate.cfg.annotations.HCANNHelper;
import org.hibernate.internal.CoreMessageLogger;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.internal.util.collections.CollectionHelper;
import org.jboss.logging.Logger;

import java.util.*;

@SuppressWarnings("all")
class PropertyContainer {
    private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, PropertyContainer.class.getName());
    private final XClass xClass;
    private final XClass entityAtStake;
    private final AccessType classLevelAccessType;
    private final List<XProperty> persistentAttributes;

    PropertyContainer(XClass clazz, XClass entityAtStake, AccessType defaultClassLevelAccessType) {
        this.xClass = clazz;
        this.entityAtStake = entityAtStake;
        if (defaultClassLevelAccessType == AccessType.DEFAULT) {
            defaultClassLevelAccessType = AccessType.PROPERTY;
        }

        AccessType localClassLevelAccessType = this.determineLocalClassDefinedAccessStrategy();

        assert localClassLevelAccessType != null;

        this.classLevelAccessType = localClassLevelAccessType != AccessType.DEFAULT ? localClassLevelAccessType : defaultClassLevelAccessType;

        assert this.classLevelAccessType == AccessType.FIELD || this.classLevelAccessType == AccessType.PROPERTY;

        List<XProperty> fields = this.xClass.getDeclaredProperties(AccessType.FIELD.getType());
        List<XProperty> getters = this.xClass.getDeclaredProperties(AccessType.PROPERTY.getType());
        this.preFilter(fields, getters);
        Map<String, XProperty> persistentAttributesFromGetters = new HashMap<>();
        LinkedHashMap<String, XProperty> localAttributeMap = new LinkedHashMap<>();
        collectPersistentAttributesUsingLocalAccessType(this.xClass, localAttributeMap, persistentAttributesFromGetters, fields, getters);
        collectPersistentAttributesUsingClassLevelAccessType(this.xClass, this.classLevelAccessType, localAttributeMap, persistentAttributesFromGetters, fields, getters);
        this.persistentAttributes = verifyAndInitializePersistentAttributes(this.xClass, localAttributeMap);
    }

    private void preFilter(List<XProperty> fields, List<XProperty> getters) {
        Iterator<XProperty> propertyIterator = fields.iterator();

        XProperty property;
        while(propertyIterator.hasNext()) {
            property = (XProperty)propertyIterator.next();
            if (mustBeSkipped(property)) {
                propertyIterator.remove();
            }
        }

        propertyIterator = getters.iterator();

        while(propertyIterator.hasNext()) {
            property = (XProperty)propertyIterator.next();
            if (mustBeSkipped(property)) {
                propertyIterator.remove();
            }
        }

    }

    private static void collectPersistentAttributesUsingLocalAccessType(XClass xClass, LinkedHashMap<String, XProperty> persistentAttributeMap, Map<String, XProperty> persistentAttributesFromGetters, List<XProperty> fields, List<XProperty> getters) {
        Iterator<XProperty> propertyIterator = fields.iterator();

        XProperty xProperty;
        Access localAccessAnnotation;
        while(propertyIterator.hasNext()) {
            xProperty = (XProperty)propertyIterator.next();
            localAccessAnnotation = (Access)xProperty.getAnnotation(Access.class);
            if (localAccessAnnotation != null && localAccessAnnotation.value() == jakarta.persistence.AccessType.FIELD) {
                propertyIterator.remove();
                persistentAttributeMap.put(xProperty.getName(), xProperty);
            }
        }

        propertyIterator = getters.iterator();

        while(propertyIterator.hasNext()) {
            xProperty = (XProperty)propertyIterator.next();
            localAccessAnnotation = (Access)xProperty.getAnnotation(Access.class);
            if (localAccessAnnotation != null && localAccessAnnotation.value() == jakarta.persistence.AccessType.PROPERTY) {
                propertyIterator.remove();
                String name = xProperty.getName();
                XProperty previous = (XProperty)persistentAttributesFromGetters.get(name);
                if (previous != null) {
                    throw new MappingException(LOG.ambiguousPropertyMethods(xClass.getName(), HCANNHelper.annotatedElementSignature(previous), HCANNHelper.annotatedElementSignature(xProperty)), new Origin(SourceType.ANNOTATION, xClass.getName()));
                }

                persistentAttributeMap.put(name, xProperty);
                persistentAttributesFromGetters.put(name, xProperty);
            }
        }

    }

    private static void collectPersistentAttributesUsingClassLevelAccessType(XClass xClass, AccessType classLevelAccessType, LinkedHashMap<String, XProperty> persistentAttributeMap, Map<String, XProperty> persistentAttributesFromGetters, List<XProperty> fields, List<XProperty> getters) {
        Iterator var6;
        XProperty getter;
        if (classLevelAccessType == AccessType.FIELD) {
            var6 = fields.iterator();

            while(var6.hasNext()) {
                getter = (XProperty)var6.next();
                if (!persistentAttributeMap.containsKey(getter.getName())) {
                    persistentAttributeMap.put(getter.getName(), getter);
                }
            }
        } else {
            var6 = getters.iterator();

            while(var6.hasNext()) {
                getter = (XProperty)var6.next();
                String name = getter.getName();
                XProperty previous = (XProperty)persistentAttributesFromGetters.get(name);
                if (previous != null) {
                    throw new MappingException(LOG.ambiguousPropertyMethods(xClass.getName(), HCANNHelper.annotatedElementSignature(previous), HCANNHelper.annotatedElementSignature(getter)), new Origin(SourceType.ANNOTATION, xClass.getName()));
                }

                if (!persistentAttributeMap.containsKey(name)) {
                    persistentAttributeMap.put(getter.getName(), getter);
                    persistentAttributesFromGetters.put(name, getter);
                }
            }
        }

    }

    public XClass getEntityAtStake() {
        return this.entityAtStake;
    }

    public XClass getDeclaringClass() {
        return this.xClass;
    }

    public AccessType getClassLevelAccessType() {
        return this.classLevelAccessType;
    }

    public Iterable<XProperty> propertyIterator() {
        return this.persistentAttributes;
    }

    private static List<XProperty> verifyAndInitializePersistentAttributes(XClass xClass, Map<String, XProperty> localAttributeMap) {
        ArrayList<XProperty> output = new ArrayList(localAttributeMap.size());
        Iterator var3 = localAttributeMap.values().iterator();

        while(var3.hasNext()) {
            XProperty xProperty = (XProperty)var3.next();
            if (!xProperty.isTypeResolved() && !discoverTypeWithoutReflection(xClass, xProperty)) {
                String msg = "Property '" + StringHelper.qualify(xClass.getName(), xProperty.getName()) + "' has an unbound type and no explicit target entity (resolve this generics usage issue or set an explicit target attribute with '@OneToMany(target=)' or use an explicit '@Type')";
                throw new AnnotationException(msg);
            }

            output.add(xProperty);
        }

        return CollectionHelper.toSmallList(output);
    }

    private AccessType determineLocalClassDefinedAccessStrategy() {
        AccessType classDefinedAccessType = AccessType.DEFAULT;
        Access access = (Access)this.xClass.getAnnotation(Access.class);
        if (access != null) {
            classDefinedAccessType = AccessType.getAccessStrategy(access.value());
        }

        return classDefinedAccessType;
    }

    private static boolean discoverTypeWithoutReflection(XClass clazz, XProperty property) {
        if (property.isAnnotationPresent(OneToOne.class) && !((OneToOne)property.getAnnotation(OneToOne.class)).targetEntity().equals(Void.TYPE)) {
            return true;
        } else if (property.isAnnotationPresent(OneToMany.class) && !((OneToMany)property.getAnnotation(OneToMany.class)).targetEntity().equals(Void.TYPE)) {
            return true;
        } else if (property.isAnnotationPresent(ManyToOne.class) && !((ManyToOne)property.getAnnotation(ManyToOne.class)).targetEntity().equals(Void.TYPE)) {
            return true;
        } else if (property.isAnnotationPresent(ManyToMany.class) && !((ManyToMany)property.getAnnotation(ManyToMany.class)).targetEntity().equals(Void.TYPE)) {
            return true;
        } else if (property.isAnnotationPresent(Any.class)) {
            return true;
        } else if (property.isAnnotationPresent(ManyToAny.class)) {
            if (!property.isCollection() && !property.isArray()) {
                throw new AnnotationException("Property '" + StringHelper.qualify(clazz.getName(), property.getName()) + "' annotated '@ManyToAny' is neither a collection nor an array");
            } else {
                return true;
            }
        } else if (property.isAnnotationPresent(Basic.class)) {
            return true;
        } else if (property.isAnnotationPresent(Type.class)) {
            return true;
        } else if (property.isAnnotationPresent(JavaType.class)) {
            return true;
        } else {
            return property.isAnnotationPresent(Target.class);
        }
    }

    private static boolean mustBeSkipped(XProperty property) {
        return property.isAnnotationPresent(Transient.class) || "net.sf.cglib.transform.impl.InterceptFieldCallback".equals(property.getType().getName());
    }
}
