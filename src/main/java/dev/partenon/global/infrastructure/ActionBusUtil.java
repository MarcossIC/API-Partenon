package dev.partenon.global.infrastructure;

import java.util.Arrays;

public class ActionBusUtil<T, V> {

    /**
     * Recibe un Handler relacionado alguna accion y busca la clase relacionada con la Acction (Query/Command/Event)
     *
     * @param handler    Instancia de handler
     * @param actionType Tipo de accion que estas realizando (Query/Command/Event)
     * @return Clase de la accion relacionada al handler
     */
    public Class<V> getActionClass(T handler, String actionType) {
        var methods = Arrays.stream(handler.getClass().getMethods())//Array de los metodos del handler
                .toList().stream()
                .filter(x -> x.getName().equalsIgnoreCase("handle"))
                .filter(x -> !x.getParameterTypes()[0].getSimpleName().startsWith(actionType))
                .toList();
        String actionClassName = methods.get(0).getParameterTypes()[0].getCanonicalName();//Recupero el nombre de la clase del primer parametro
        return getClass(actionClassName);
    }

    /**
     * Si existe una clase que tenga el mismo nombre la devuelve
     *
     * @param name Nombre de alguna clase
     * @return Retorna la clase o un valor nulo en caso de no existir clase
     */
    public Class<V> getClass(String name) {
        try {
            return (Class<V>) Class.forName(name);
        } catch (Exception ex) {
            return null;
        }
    }

}
