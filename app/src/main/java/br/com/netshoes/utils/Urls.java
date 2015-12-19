package br.com.netshoes.utils;

/**
 * Created by Caramelo on 17/12/2015.
 */
public abstract class Urls {
    public final static String NETSHOES = "http://www.netshoes.com.br";
    public final static String DEPARTAMENTO = Urls.NETSHOES+"/departamento";
    public final static String DEPARTAMENTO_PRINCIPAL = Urls.DEPARTAMENTO+"?Nr=OR(product.productType.displayName:T%C3%AAnis,product.productType.displayName:T%C3%AAnis)";
}
