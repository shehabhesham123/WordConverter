package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.internal.artifacts.dependencies.ProjectDependencyInternal;
import org.gradle.api.internal.artifacts.DefaultProjectDependencyFactory;
import org.gradle.api.internal.artifacts.dsl.dependencies.ProjectFinder;
import org.gradle.api.internal.catalog.DelegatingProjectDependency;
import org.gradle.api.internal.catalog.TypeSafeProjectDependencyFactory;
import javax.inject.Inject;

@NonNullApi
public class FeaturesProjectDependency extends DelegatingProjectDependency {

    @Inject
    public FeaturesProjectDependency(TypeSafeProjectDependencyFactory factory, ProjectDependencyInternal delegate) {
        super(factory, delegate);
    }

    /**
     * Creates a project dependency on the project at path ":features:interpreter"
     */
    public Features_InterpreterProjectDependency getInterpreter() { return new Features_InterpreterProjectDependency(getFactory(), create(":features:interpreter")); }

    /**
     * Creates a project dependency on the project at path ":features:render"
     */
    public Features_RenderProjectDependency getRender() { return new Features_RenderProjectDependency(getFactory(), create(":features:render")); }

}
