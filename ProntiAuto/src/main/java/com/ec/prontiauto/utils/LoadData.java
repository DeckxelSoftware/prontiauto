package com.ec.prontiauto.utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ec.prontiauto.entidad.AbonoPrestamo;
import com.ec.prontiauto.entidad.Agencia;
import com.ec.prontiauto.entidad.Area;
import com.ec.prontiauto.entidad.Articulo;
import com.ec.prontiauto.entidad.AsientoContableCabecera;
import com.ec.prontiauto.entidad.AsientoContableDetAdicional;
import com.ec.prontiauto.entidad.AutorLibro;
import com.ec.prontiauto.entidad.Banco;
import com.ec.prontiauto.entidad.Cargo;
import com.ec.prontiauto.entidad.CargoVacacion;
import com.ec.prontiauto.entidad.Cheque;
import com.ec.prontiauto.entidad.Chequera;
import com.ec.prontiauto.entidad.Cliente;
import com.ec.prontiauto.entidad.ClienteEnGrupo;
import com.ec.prontiauto.entidad.ConfiguracionGeneral;
import com.ec.prontiauto.entidad.Contrato;
import com.ec.prontiauto.entidad.CuentaBancariaEmpresa;
import com.ec.prontiauto.entidad.CuentaContable;
import com.ec.prontiauto.entidad.Cuota;
import com.ec.prontiauto.entidad.DetalleNovedadRolPago;
import com.ec.prontiauto.entidad.DivisionAdministrativa;
import com.ec.prontiauto.entidad.Empresa;
import com.ec.prontiauto.entidad.Grupo;

import com.ec.prontiauto.entidad.HistorialLaboral;
import com.ec.prontiauto.entidad.HistoricoPlanContrato;
import com.ec.prontiauto.entidad.HistoricoRol;
import com.ec.prontiauto.entidad.ImpuestoRenta;
import com.ec.prontiauto.entidad.InformacionFinanciera;
import com.ec.prontiauto.entidad.ItemCobroPago;
import com.ec.prontiauto.entidad.LibroBiblioteca;
import com.ec.prontiauto.entidad.Licitacion;
import com.ec.prontiauto.entidad.ListaValoresDetalle;
import com.ec.prontiauto.entidad.ListaValoresTipo;
import com.ec.prontiauto.entidad.Memorandum;
import com.ec.prontiauto.entidad.OrdenCompra;
import com.ec.prontiauto.entidad.PagosDos;
import com.ec.prontiauto.entidad.PagosUno;
import com.ec.prontiauto.entidad.PeriodoContable;
import com.ec.prontiauto.entidad.PeriodoLaboral;
import com.ec.prontiauto.entidad.Permiso;
import com.ec.prontiauto.entidad.Plan;
import com.ec.prontiauto.entidad.Prestamo;
import com.ec.prontiauto.entidad.Proveedor;
import com.ec.prontiauto.entidad.Refinanciamiento;
import com.ec.prontiauto.entidad.Region;
import com.ec.prontiauto.entidad.RegistroVacacion;
import com.ec.prontiauto.entidad.Revision;
import com.ec.prontiauto.entidad.Rol;
import com.ec.prontiauto.entidad.RolPago;
import com.ec.prontiauto.entidad.RolPermiso;
import com.ec.prontiauto.entidad.RolUsuario;
import com.ec.prontiauto.entidad.RubrosRol;
import com.ec.prontiauto.entidad.SriGastos;

import com.ec.prontiauto.entidad.Supervisor;
import com.ec.prontiauto.entidad.Trabajador;
import com.ec.prontiauto.entidad.TransaccionAsientoContable;
import com.ec.prontiauto.entidad.Usuario;
import com.ec.prontiauto.entidad.Vendedor;
import com.ec.prontiauto.facturacion.entidad.Factura;
import com.ec.prontiauto.facturacion.entidad.NotaCredito;

@Component
public class LoadData {
        @Value("${spring.jpa.hibernate.ddl-auto}")
        private String loadData;

        @Autowired
        ReadCsv readCsv;

        public void loadData() {
                if (loadData.equals("create")) {
                        this.deleteDataFromDatabase();
                        Path path = Paths.get("").toAbsolutePath();
                        // Path pathFolder = path.getRoot().resolve(path.subpath(0, path.getNameCount()
                        // - 1)).resolve("datos");
                        Path pathFolder = path.resolve("src").resolve("main").resolve("resources").resolve("data");
                        this.loadDataFathers(pathFolder);
                        this.loadDataChilds(pathFolder);
                }
        }

        public void loadDataFathers(Path pathFolder) {
                List<String> listPathFathers = new ArrayList<>();
                listPathFathers.add("001_impuesto_renta.csv");
                listPathFathers.add("001_region.csv");
                listPathFathers.add("001_nota_credito.csv");
                listPathFathers.add("001_articulo.csv");
                listPathFathers.add("001_banco.csv");
                listPathFathers.add("001_empresa.csv");
                listPathFathers.add("001_libro_biblioteca.csv");
                listPathFathers.add("001_lista_valores_tipo.csv");
                listPathFathers.add("001_permiso.csv");
                listPathFathers.add("001_rol.csv");
                listPathFathers.add("001_usuario.csv");
                listPathFathers.add("001_grupo.csv");
                listPathFathers.add("001_configuracion_general.csv");
                listPathFathers.add("001_plan.csv");
                listPathFathers.add("001_periodo_contable.csv");
                listPathFathers.add("001_area.csv");
                listPathFathers.add("001_periodo_laboral.csv");
                listPathFathers.add("001_division_administrativa.csv");
                listPathFathers.add("001_rubros_rol.csv");
                listPathFathers.add("001_pagos_uno.csv");
                List<Class<?>> listObjectFathers = new ArrayList<>();
                listObjectFathers.add(ImpuestoRenta.class);
                listObjectFathers.add(Region.class);
                listObjectFathers.add(NotaCredito.class);
                listObjectFathers.add(Articulo.class);
                listObjectFathers.add(Banco.class);
                listObjectFathers.add(Empresa.class);
                listObjectFathers.add(LibroBiblioteca.class);
                listObjectFathers.add(ListaValoresTipo.class);
                listObjectFathers.add(Permiso.class);
                listObjectFathers.add(Rol.class);
                listObjectFathers.add(Usuario.class);
                listObjectFathers.add(Grupo.class);
                listObjectFathers.add(ConfiguracionGeneral.class);
                listObjectFathers.add(Plan.class);
                listObjectFathers.add(PeriodoContable.class);
                listObjectFathers.add(Area.class);
                listObjectFathers.add(PeriodoLaboral.class);
                listObjectFathers.add(DivisionAdministrativa.class);
                listObjectFathers.add(RubrosRol.class);
                listObjectFathers.add(PagosUno.class);
                for (int i = 0; i < listPathFathers.size(); i++) {
                        readCsv.readAndLoadDataCsv(listObjectFathers.get(i),
                                        pathFolder.resolve(listPathFathers.get(i)).toString());
                }
        }

        public void loadDataChilds(Path pathFolder) {
                List<String> listPathChildren = new ArrayList<>();
                listPathChildren.add("002_agencia.csv");
                listPathChildren.add("002_autor_libro.csv");
                listPathChildren.add("002_cliente.csv");
                listPathChildren.add("002_lista_valores_detalle.csv");
                listPathChildren.add("002_rol_permiso.csv");
                listPathChildren.add("002_rol_usuario.csv");
                listPathChildren.add("002_revision.csv");
                // 5
                listPathChildren.add("002_trabajador.csv");
                listPathChildren.add("002_cuenta_contable.csv");
                listPathChildren.add("002_cargo.csv");
                listPathChildren.add("003_supervisor.csv");
                listPathChildren.add("003_sri_gastos.csv");
                listPathChildren.add("003_item_cobro_pago.csv");
                listPathChildren.add("003_pagos_dos.csv");
                // 10
                listPathChildren.add("003_vendedor.csv");
                listPathChildren.add("003_cliente_en_grupo.csv");
                listPathChildren.add("003_contrato.csv");
                listPathChildren.add("003_historial_laboral.csv");
                listPathChildren.add("003_memorandum.csv");
                // 15
                listPathChildren.add("003_informacion_financiera.csv");
                listPathChildren.add("004_histocio_plan_contrato.csv");
                listPathChildren.add("004_cuenta_bancaria_empresa.csv");
                // 20
                listPathChildren.add("004_licitacion.csv");
                listPathChildren.add("005_cuota.csv");
                listPathChildren.add("005_refinanciamiento.csv");
                listPathChildren.add("005_chequera.csv");
                // 25
                listPathChildren.add("006_cheque.csv");

                List<Class<?>> listObjectChildren = new ArrayList<>();
                listObjectChildren.add(Agencia.class);
                listObjectChildren.add(AutorLibro.class);
                listObjectChildren.add(Cliente.class);
                listObjectChildren.add(ListaValoresDetalle.class);
                listObjectChildren.add(RolPermiso.class);
                listObjectChildren.add(RolUsuario.class);
                listObjectChildren.add(Revision.class);
                // 5
                listObjectChildren.add(Trabajador.class);
                listObjectChildren.add(CuentaContable.class);
                listObjectChildren.add(Cargo.class);
                listObjectChildren.add(Supervisor.class);
                listObjectChildren.add(SriGastos.class);
                listObjectChildren.add(ItemCobroPago.class);
                listObjectChildren.add(PagosDos.class);
                // 10
                listObjectChildren.add(Vendedor.class);
                listObjectChildren.add(ClienteEnGrupo.class);
                listObjectChildren.add(Contrato.class);
                listObjectChildren.add(HistorialLaboral.class);
                listObjectChildren.add(Memorandum.class);
                // 15
                listObjectChildren.add(InformacionFinanciera.class);
                listObjectChildren.add(HistoricoPlanContrato.class);
                listObjectChildren.add(CuentaBancariaEmpresa.class);
                // 20
                listObjectChildren.add(Licitacion.class);
                listObjectChildren.add(Cuota.class);
                listObjectChildren.add(Refinanciamiento.class);
                listObjectChildren.add(Chequera.class);
                // 25
                listObjectChildren.add(Cheque.class);
                String[][] listParamsFather = {
                                { "IdRegion" },
                                { "IdLibroBiblioteca" },
                                { "IdUsuario", "IdEmpresa" },
                                { "IdListaValoresTipo" },
                                { "IdRol", "IdPermiso" },
                                { "IdRol", "IdUsuario" },
                                { "IdArticulo" },

                                // 5
                                { "IdUsuario", "IdAgencia" },
                                { "IdPeriodoContable" },
                                { "IdArea" },
                                { "IdTrabajador", "IdAgencia" },
                                { "IdTrabajador" },
                                { "IdCuentaContable" },
                                { "IdPagosUno", "IdTrabajador", "IdPeriodoLaboral" },
                                // 10
                                { "IdTrabajador", "IdAgencia" },
                                { "IdCliente", "IdGrupo" },
                                { "IdClienteEnGrupo", "IdVendedor" },
                                { "IdTrabajador", "IdAgencia", "IdCargo", "IdDivisionAdministrativa" },
                                { "IdTrabajador" },
                                // 15
                                { "IdTrabajador" },
                                { "IdContrato", "IdPlan" },
                                { "IdBanco", "IdEmpresa", "IdInformacionFinanciera" },
                                // 20
                                { "IdContrato" },
                                { "IdHistoricoPlanContrato" },
                                { "IdHistoricoPlanContrato" },
                                { "IdCuentaBancariaEmpresa" },
                                // 25
                                { "IdChequera" },

                };
                Class<?>[][] lisObjectRelationFathers = {
                                { Region.class },
                                { LibroBiblioteca.class },
                                { Usuario.class, Empresa.class },
                                { ListaValoresTipo.class },
                                { Rol.class, Permiso.class },
                                { Rol.class, Usuario.class },
                                { Articulo.class },

                                // 5
                                { Usuario.class, Agencia.class },
                                { PeriodoContable.class },
                                { Area.class },
                                { Trabajador.class, Agencia.class },
                                { Trabajador.class },
                                { CuentaContable.class },
                                { PagosUno.class, Trabajador.class, PeriodoLaboral.class },
                                // 10
                                { Trabajador.class, Agencia.class },
                                { Cliente.class, Grupo.class },
                                { ClienteEnGrupo.class, Vendedor.class },
                                { Trabajador.class, Agencia.class, Cargo.class, DivisionAdministrativa.class },
                                { Trabajador.class },
                                // 15
                                { Trabajador.class },
                                { Contrato.class, Plan.class },
                                { Banco.class, Empresa.class, InformacionFinanciera.class },
                                // 20
                               // { CargoVacacion.class },
                                { Contrato.class },
                                { HistoricoPlanContrato.class },
                                { HistoricoPlanContrato.class },
                                { CuentaBancariaEmpresa.class },
                                // 25
                                { Chequera.class },
                };

                int count = 0;
                for (Class<?>[] clazzesFathers : lisObjectRelationFathers) {
                        readCsv.readAndLoadDataWithRelationCsv(clazzesFathers, listObjectChildren.get(count),
                                        listParamsFather[count],
                                        listParamsFather[count],
                                        pathFolder.resolve(listPathChildren.get(count)).toString());
                        count++;
                }
        }

        public void deleteDataFromDatabase() {
                List<Class<?>> listObjectFathers = new ArrayList<>();
                listObjectFathers.add(Factura.class);
                listObjectFathers.add(NotaCredito.class);
                listObjectFathers.add(Agencia.class);
                listObjectFathers.add(Banco.class);
                listObjectFathers.add(Empresa.class);
                listObjectFathers.add(LibroBiblioteca.class);
                listObjectFathers.add(ListaValoresTipo.class);
                listObjectFathers.add(Permiso.class);
                listObjectFathers.add(Rol.class);
                listObjectFathers.add(Usuario.class);
                listObjectFathers.add(Grupo.class);
                listObjectFathers.add(ConfiguracionGeneral.class);
                listObjectFathers.add(Plan.class);
                listObjectFathers.add(AutorLibro.class);
                listObjectFathers.add(Cliente.class);
                listObjectFathers.add(CuentaBancariaEmpresa.class);
                listObjectFathers.add(ListaValoresDetalle.class);
                listObjectFathers.add(RolPermiso.class);
                listObjectFathers.add(RolUsuario.class);
                listObjectFathers.add(Trabajador.class);
                listObjectFathers.add(Supervisor.class);
                listObjectFathers.add(Vendedor.class);
                listObjectFathers.add(ClienteEnGrupo.class);
                listObjectFathers.add(Contrato.class);
                listObjectFathers.add(HistoricoPlanContrato.class);
                listObjectFathers.add(Cuota.class);
                listObjectFathers.add(Refinanciamiento.class);
//                listObjectFathers.add(GrupoContable.class);
//                listObjectFathers.add(SubgrupoContable.class);
                listObjectFathers.add(PeriodoContable.class);
                listObjectFathers.add(CuentaContable.class);
                listObjectFathers.add(Chequera.class);
                listObjectFathers.add(Cheque.class);
                listObjectFathers.add(AsientoContableCabecera.class);
                listObjectFathers.add(TransaccionAsientoContable.class);
                listObjectFathers.add(AsientoContableDetAdicional.class);
                listObjectFathers.add(Cargo.class);
                listObjectFathers.add(HistorialLaboral.class);
                listObjectFathers.add(Area.class);
                listObjectFathers.add(Memorandum.class);
                listObjectFathers.add(InformacionFinanciera.class);
                listObjectFathers.add(PeriodoLaboral.class);
                listObjectFathers.add(CargoVacacion.class);
                listObjectFathers.add(RegistroVacacion.class);
                listObjectFathers.add(Prestamo.class);
                listObjectFathers.add(Licitacion.class);
                listObjectFathers.add(SriGastos.class);
                listObjectFathers.add(Proveedor.class);
                listObjectFathers.add(OrdenCompra.class);
                listObjectFathers.add(Revision.class);
                listObjectFathers.add(RubrosRol.class);
                listObjectFathers.add(DetalleNovedadRolPago.class);
                listObjectFathers.add(ItemCobroPago.class);
                listObjectFathers.add(RolPago.class);
                listObjectFathers.add(PagosDos.class);
                listObjectFathers.add(HistoricoRol.class);

                readCsv.deleteDataFromTable(listObjectFathers);
        }
}
