module code {
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires javafx.graphics;
    requires javafx.controls;

    exports code.graphics;
    exports code.model;
    exports code.model.objects;
    exports code.model.data.loaders;
    exports code.utils;
    exports code.algorithms;
    exports code.algorithms.Solvers;
}
