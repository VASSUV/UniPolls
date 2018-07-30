package ${packageName}.presentation${dotSubpackage};

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ${packageName}.presentation${dotSubpackage}.${viewName};
import ${packageName}.presentation${dotSubpackage}.${presenterName};

import ${superClassFqcn};

<#if packageName??>
import ${packageName}.R;
</#if>

import com.arellomobile.mvp.presenter.InjectPresenter;
<#if createProvidesMethod>
import com.arellomobile.mvp.presenter.ProvidePresenter;
</#if>

public class ${className} extends ${superClassName} implements ${viewName} {
    public static final String TAG = "${className}";
	@InjectPresenter
	${presenterName} presenter;

<#if createProvidesMethod>
        @ProvidePresenter
        ${presenterName} providePresenter() {
            return new ${presenterName}();
        }
</#if>

<#if includeFactory>
    public static ${className} newInstance(Bundle args) {
        ${className} fragment = new ${className}();
        fragment.setArguments(args);
        return fragment;
    }
</#if>

    @Override
    public View onCreateView(final LayoutInflater inflater, final   ViewGroup container,
            final Bundle savedInstanceState) {
<#if includeLayout>
        return inflater.inflate(R.layout.${fragmentName}, container, false);
</#if>
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
