# Dynamic Images

This plugin is designed to easy image handling in Hippo CMS or Bloomreach Experience.

Tired of defining 30+ variants on your imageset? Tired of all the work to make a variant 1px wider? Are your editors tired of scaling and cropping all the variants of all images?

With this plugin image sizes are defined in the view template and variants are generated on-the-fly when a link to the variant is created.




## Installation
1. Add dependency to pom.xml

`    <dependency>
      <groupId>nl.openweb.hippo</groupId>
      <artifactId>dynamic-images</artifactId>
      <version>0.01.00-SNAPSHOT</version>
    </dependency>
    <dependency>
      <groupId>org.onehippo.cms7</groupId>
      <artifactId>hippo-cms-gallery-frontend</artifactId>
    </dependency>`
    
2. Add a Spring configuration file in hst-assembly/overrides.

`<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">
  <bean id="nl.openweb.hippo.dynamicimage.strategy.VariantStrategy"
        class="nl.openweb.hippo.dynamicimage.strategy.CenteredVariantStrategy"/>
  <bean id="nl.openweb.hippo.dynamicimage.service.VariantService"
        class="nl.openweb.hippo.dynamicimage.service.DefaultVariantService">
    <property name="timeoutMilliseconds" value="1000"/>
    <property name="variantStrategy" ref="nl.openweb.hippo.dynamicimage.strategy.VariantStrategy"/>
  </bean>
</beans>`

As you can see it's possible to customize the timeout and the various classes. Also the ScalingGalleryProcessor can be overwritten and set with Spring configuration on the VariantStrategy.


3. Add taglib for JSP.

`<%@ taglib prefix="dynimg" uri="http://www.openweb.nl/hippo/dynamicimages/taglib" %>`

4. Add group sitewriters to domain hippogallery

Add value `sitewriters` to property `hipposys:groups` at path=/hippo:configuration/hippo:domains/hippogallery/hippo:authrole

## Usage

Instead of using <hst:link to get a link to an image you can now use <dynimg:link to get the link. 
dynimg has three attributes: 
- _imagebean_: an existing variant of type HippoGalleryImageBean (required)
- _width_: the width of the variant to be linked. Specifying no value of 0 means the original width will be used
- _height_: the height of the variant to be linked. Specifying no value of 0 means the original height will be used

The first time the link is created a new variant will be created for you in the repository. 
You can use any existing variant as a source. Using the original is possible, but it's also possible to define a 
landscape and portrait variant and reference one of those.

## Examples

In a default Hippo CMS project a link to an image is generated by using

`<hst:link var="imageUrl" hippobean="${document.image.homepageBanner}"/>`

the scaling properties are configured in the configuration.


With Dynamic Images the scaling is configured in the view (JSP or Freemarker template).
To generate the URL you can now use

`<dynimg:link var="imageUrl" imagebean="${document.image.original}" width="1312" height="580"/>`

The original image will be scaled and cropped to the requested width and height.
By leaving the height unspecified an image is only scaled to the requested width.

`<dynimg:link var="imageUrl" imagebean="${document.image.original}" width="1312"/>`

